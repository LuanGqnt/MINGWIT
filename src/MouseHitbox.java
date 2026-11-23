import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;

public class MouseHitbox {

    private final Rectangle bounds;
    private final Runnable onClick;
    private boolean hovering;
    private BooleanSupplier blockClicks = null;

    public MouseHitbox(int x, int y, int width, int height, Runnable onClick) {
        this.bounds = new Rectangle(x, y, width, height);
        this.onClick = onClick;
    }

    public MouseHitbox setBlockClicks(BooleanSupplier blockClicks) {
        this.blockClicks = blockClicks;
        return this;
    }

    public boolean isClickBlocked() {
        return blockClicks != null && blockClicks.getAsBoolean();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isHovering() {
        return hovering;
    }

    public boolean contains(Point p) {
        return p != null && bounds.contains(p);
    }

    public boolean updateHover(Point p, Component cursorTarget) {
        boolean nowHovering = contains(p);
        boolean changed = (nowHovering != hovering);
        hovering = nowHovering;

        if (cursorTarget != null) {
            cursorTarget.setCursor(Cursor.getPredefinedCursor(
                hovering ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR
            ));
        }

        return changed;
    }

    public void clearHover(Component cursorTarget) {
        if (hovering) {
            hovering = false;
            if (cursorTarget != null) {
                cursorTarget.setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    public boolean handleClick(Point p) {
        if (isClickBlocked())
            return false;
        
        if (contains(p)) {
            if (onClick != null) {
                onClick.run();
            }
            return true;
        }
        return false;
    }

    public void drawDebug(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(2f));
        g2d.setColor(hovering ? Color.YELLOW : Color.GREEN);
        g2d.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public MouseAdapter createMouseAdapter(Component owner, MouseHitbox... others) {
        List<MouseHitbox> allBoxes = new ArrayList<>();
        allBoxes.add(this);
        if (others != null) {
            allBoxes.addAll(Arrays.asList(others));
        }

        return new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                boolean anyHovering = false;
                boolean changed = false;

                for (MouseHitbox box : allBoxes) {
                    boolean nowHovering = box.contains(p) && !box.isClickBlocked();
                    if (nowHovering != box.hovering) {
                        box.hovering = nowHovering;
                        changed = true;
                    }
                    if (nowHovering) {
                        anyHovering = true;
                    }
                }

                if (owner != null) {
                    owner.setCursor(Cursor.getPredefinedCursor(
                        anyHovering ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR
                    ));
                    if (changed) {
                        owner.repaint();
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boolean changed = false;
                for (MouseHitbox box : allBoxes) {
                    if (box.hovering) {
                        box.hovering = false;
                        changed = true;
                    }
                }

                if (owner != null) {
                    owner.setCursor(Cursor.getDefaultCursor());
                    if (changed) {
                        owner.repaint();
                    }
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                for (MouseHitbox box : allBoxes) {
                    if (box.handleClick(p)) {
                        break;
                    }
                }
            }
        };
    }
}