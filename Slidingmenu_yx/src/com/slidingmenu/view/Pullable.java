package com.slidingmenu.view;

public interface Pullable
{
	/**
	 * ??��???????????以�?????�?�????�????�?�?????????��??以�?��??return false
	 * 
	 * @return true�???????以�???????????�????false
	 */
	boolean canPullDown();

	/**
	 * ??��???????????以�?????�?�????�????�?�?????????��??以�?��??return false
	 * 
	 * @return true�???????以�???????????�????false
	 */
	boolean canPullUp();
}