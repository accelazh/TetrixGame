package com.accela.tetrixgame.gamelogic.rectMover.scrolling;

import com.accela.tetrixgame.gamelogic.geometry.Rectangle;

/**
 * 
 * 匀速竖直移动的移动代理
 * 
 */
public class UniformSpeedDownMoveProxy
{
	private double initS;

	private double curS;

	private double totalS;

	private boolean movingDown;

	private boolean hasFinished = false;

	private double remainedMoveDistance = 0;

	private double maxVel;

	private double stillS;

	private double curStillS;

	@SuppressWarnings("unused")
	private UniformSpeedDownMoveProxy()
	{

	}

	public UniformSpeedDownMoveProxy(double initS, double totalS,
			double stillS, double maxVel, boolean movingDown)
	{
		if (initS < 0)
		{
			throw new IllegalArgumentException("initS should be non negtive");
		}
		if (totalS <= 0)
		{
			throw new IllegalArgumentException("totalS should be positive");
		}
		if (totalS < initS)
		{
			throw new IllegalArgumentException(
					"totalS should be greater than initS");
		}
		if (stillS < 0)
		{
			throw new IllegalArgumentException("stillS should be nonnegtive");
		}
		if (maxVel <= 0)
		{
			throw new IllegalArgumentException("maxVel should be positive");
		}

		this.initS = initS;
		this.curS = initS;
		this.totalS = totalS;
		this.movingDown = movingDown;
		this.maxVel = maxVel;
		this.stillS = stillS;
		this.curStillS = 0;
	}

	public void update(Rectangle rect, long interpolation)
	{
		if (null == rect)
		{
			throw new NullPointerException("rect should not be null");
		}
		if (interpolation < 0)
		{
			throw new IllegalArgumentException(
					"interpolation should not be negtive");
		}

		double dertStillS = getVelocity() * interpolation;
		if (curStillS < stillS)
		{
			curStillS += dertStillS;
			return;
		}

		double dertS = getVelocity() * interpolation;
		dertS = Math.min(dertS, totalS - curS);
		if (curS >= totalS)
		{
			hasFinished = true;
			dertS = 0;
		}
		curS += dertS;

		int actualMoveDistance = (int) Math.round(dertS + remainedMoveDistance);
		remainedMoveDistance = (dertS + remainedMoveDistance)
				- actualMoveDistance;

		if (movingDown)
		{
			rect.moveBy(0, actualMoveDistance);
		} else
		{
			rect.moveBy(0, -actualMoveDistance);
		}

	}

	public double getVelocity()
	{
		return maxVel;
	}

	public boolean hasFinished()
	{
		return hasFinished;
	}

	public void reset()
	{
		curS = initS;
		curStillS = 0;
		remainedMoveDistance = 0;
		hasFinished = false;
	}

}
