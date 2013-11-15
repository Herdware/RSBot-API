package org.powerbot.client.input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.powerbot.bot.RawAWTEvent;
import org.powerbot.bot.SelectiveEventQueue;

public abstract class Mouse extends Focus implements MouseListener, MouseMotionListener, MouseWheelListener {
	private int clientX;
	private int clientY;
	private int clientPressX = -1;
	private int clientPressY = -1;
	private long clientPressTime = -1;
	private boolean clientPresent;
	private boolean clientPressed;

	public abstract void _mouseClicked(MouseEvent e);

	public abstract void _mouseDragged(MouseEvent e);

	public abstract void _mouseEntered(MouseEvent e);

	public abstract void _mouseExited(MouseEvent e);

	public abstract void _mouseMoved(MouseEvent e);

	public abstract void _mousePressed(MouseEvent e);

	public abstract void _mouseReleased(MouseEvent e);

	public abstract void _mouseWheelMoved(MouseWheelEvent e);

	public int getX() {
		if (clientX == -1) {
			return 0;
		}
		return clientX;
	}

	public int getY() {
		if (clientY == -1) {
			return 0;
		}
		return clientY;
	}

	public Point getLocation() {
		return new Point(clientX == -1 ? 0 : clientX, clientY == -1 ? 0 : clientY);
	}

	public int getPressX() {
		return clientPressX;
	}

	public int getPressY() {
		return clientPressY;
	}

	public Point getPressLocation() {
		return new Point(clientPressX, clientPressY);
	}

	public long getPressTime() {
		return clientPressTime;
	}

	public boolean isPressed() {
		return clientPressed;
	}

	public boolean isPresent() {
		return clientPresent;
	}

	@Override
	public final void mouseClicked(final MouseEvent e) {
		clientX = e.getX();
		clientY = e.getY();
		_mouseClicked(e);
	}

	@Override
	public final void mouseDragged(final MouseEvent e) {
		clientX = e.getX();
		clientY = e.getY();
		_mouseDragged(e);
	}

	@Override
	public final void mouseEntered(final MouseEvent e) {
		clientPresent = true;
		clientX = e.getX();
		clientY = e.getY();
		_mouseEntered(e);
	}

	@Override
	public final void mouseExited(final MouseEvent e) {
		clientPresent = false;
		clientX = e.getX();
		clientY = e.getY();
		_mouseExited(e);
	}

	@Override
	public final void mouseMoved(final MouseEvent e) {
		clientX = e.getX();
		clientY = e.getY();
		_mouseMoved(e);
	}

	@Override
	public final void mousePressed(final MouseEvent e) {
		clientPressed = true;
		clientX = e.getX();
		clientY = e.getY();
		clientPressX = e.getX();
		clientPressY = e.getY();
		clientPressTime = System.currentTimeMillis();
		_mousePressed(e);
	}

	@Override
	public final void mouseReleased(final MouseEvent e) {
		clientX = e.getX();
		clientY = e.getY();
		clientPressed = false;

		_mouseReleased(e);
	}

	@Override
	public void mouseWheelMoved(final MouseWheelEvent e) {
		try {
			_mouseWheelMoved(e);
		} catch (final AbstractMethodError ignored) {
		}
	}

	public final void sendEvent(final MouseEvent e) {
		if (e == null) {
			return;
		}
		SelectiveEventQueue.getInstance().postEvent(new RawAWTEvent(e));
	}
}