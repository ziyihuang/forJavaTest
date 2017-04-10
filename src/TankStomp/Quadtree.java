package TankStomp;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* 
    四叉树节点包含：
- objects: 用于存储物体对象
- nodes: 存储四个子节点
- level: 该节点的深度，根节点的默认深度为0
- bounds: 该节点对应的象限在屏幕上的范围，bounds是一个矩形
*/
public class Quadtree {

	private int MAX_OBJECTS = 10;
	private int MAX_LEVELS = 5;

	private int level;
	private List<Rectangle> objects;
	private Rectangle bounds;
	private Quadtree[] nodes;

	/*
	 * Constructor
	 */
	public Quadtree(int pLevel, Rectangle pBounds) {
		level = pLevel;
		objects = new ArrayList<>();
		bounds = pBounds;
		nodes = new Quadtree[4];
	}

	public int getMAX_OBJECTS() {
		return MAX_OBJECTS;
	}

	public void setMAX_OBJECTS(int mAX_OBJECTS) {
		MAX_OBJECTS = mAX_OBJECTS;
	}

	public int getMAX_LEVELS() {
		return MAX_LEVELS;
	}

	public void setMAX_LEVELS(int mAX_LEVELS) {
		MAX_LEVELS = mAX_LEVELS;
	}

	/*
	 * Clears the quadtree
	 */
	public void clear() {
		objects.clear();

		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}

	/*
	 * Splits the node into 4 subnodes
	 */
	private void split() {
		int subWidth = (int) (bounds.getWidth() / 2);
		int subHeight = (int) (bounds.getHeight() / 2);
		int x = (int) bounds.getX();
		int y = (int) bounds.getY();

		nodes[0] = new Quadtree(level + 1, new Rectangle(x + subWidth, y, subWidth, subHeight));
		nodes[1] = new Quadtree(level + 1, new Rectangle(x, y, subWidth, subHeight));
		nodes[2] = new Quadtree(level + 1, new Rectangle(x, y + subHeight, subWidth, subHeight));
		nodes[3] = new Quadtree(level + 1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
	}

	/*
	 * Determine which node the object belongs to. -1 means object cannot
	 * completely fit within a child node and is part of the parent node
	 */
	private int getIndex(Rectangle pRect) {
		int index = -1;
		double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
		double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);

		// Object can completely fit within the top quadrants
		boolean topQuadrant = (pRect.getY() < horizontalMidpoint
				&& pRect.getY() + pRect.getHeight() < horizontalMidpoint);
		// Object can completely fit within the bottom quadrants
		boolean bottomQuadrant = (pRect.getY() > horizontalMidpoint);

		// Object can completely fit within the left quadrants
		if (pRect.getX() < verticalMidpoint && pRect.getX() + pRect.getWidth() < verticalMidpoint) {
			if (topQuadrant) {
				index = 1;
			} else if (bottomQuadrant) {
				index = 2;
			}
		}
		// Object can completely fit within the right quadrants
		else if (pRect.getX() > verticalMidpoint) {
			if (topQuadrant) {
				index = 0;
			} else if (bottomQuadrant) {
				index = 3;
			}
		}

		return index;
	}

	/*
	 * Insert the object into the quadtree. If the node exceeds the capacity, it
	 * will split and add all objects to their corresponding nodes.
	 */
	public void insert(Rectangle pRect) {
		if (nodes[0] != null) {
			int index = getIndex(pRect);

			if (index != -1) {
				nodes[index].insert(pRect);

				return;
			}
		}

		objects.add(pRect);

		if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
			split();

			int i = 0;
			while (i < objects.size()) {
				int index = getIndex(objects.get(i));
				if (index != -1) {
					nodes[index].insert(objects.remove(i));
				} else {
					i++;
				}
			}
		}
	}

	/*
	 * Return all objects that could collide with the given object
	 */
	public List<Rectangle> retrieve(List<Rectangle> returnObjects, Rectangle pRect) {
		int index = getIndex(pRect);
		if (index != -1 && nodes[0] != null) {
			nodes[index].retrieve(returnObjects, pRect);
		}

		returnObjects.addAll(objects);

		return returnObjects;
	}

	public static void main(String args[]) {
		Map<Rectangle, Integer> map = new HashMap<>();
		Rectangle rect = new Rectangle(24, 24, 12, 12);
		map.put(rect, 1);
		
		rect = new Rectangle(24, 36, 12, 12);
		map.put(rect, 2);
		
		rect = new Rectangle(24, 48, 12, 12);
		map.put(rect, 3);
		
		rect = new Rectangle(116, 40, 24, 24);
		map.put(rect, 4);
		
		rect = new Rectangle(380, 24, 12, 12);
		map.put(rect, 5);
		
		rect = new Rectangle(380, 36, 12, 12);
		map.put(rect, 6);
		
		rect = new Rectangle(380, 48, 12, 12);
		map.put(rect, 7);
		
		rect = new Rectangle(56, 86, 24, 24);
		map.put(rect, 8);
		
		rect = new Rectangle(116, 126, 24, 24);
		map.put(rect, 9);
		
		rect = new Rectangle(320, 120, 24, 24);
		map.put(rect, 10);
		
		rect = new Rectangle(100, 184, 12, 12);
		map.put(rect, 11);
		
		rect = new Rectangle(100, 196, 12, 12);
		map.put(rect, 12);
		
		rect = new Rectangle(100, 208, 12, 12);
		map.put(rect, 13);
		
		rect = new Rectangle(266, 196, 24, 24);
		map.put(rect, 14);
		
		rect = new Rectangle(384, 184, 12, 12);
		map.put(rect, 15);
		
		rect = new Rectangle(384, 196, 12, 12);
		map.put(rect, 16);
		
		rect = new Rectangle(384, 208, 12, 12);
		map.put(rect, 17);
		
		rect = new Rectangle(24, 240, 24, 24);
		map.put(rect, 18);
		
		rect = new Rectangle(206, 276, 24, 24);
		map.put(rect, 19);
		
		rect = new Rectangle(426, 306, 24, 24);
		map.put(rect, 20);
		
		rect = new Rectangle(70, 330, 24, 24);
		map.put(rect, 21);
		
		rect = new Rectangle(290, 306, 24, 24);
		map.put(rect, 22);
		
		rect = new Rectangle(30, 440, 24, 24);
		map.put(rect, 23);
		
		rect = new Rectangle(238, 433, 24, 24);
		map.put(rect, 24);
		
		rect = new Rectangle(150, 380, 12, 12);
		map.put(rect, 25);
		
		rect = new Rectangle(150, 392, 12, 12);
		map.put(rect, 26);
		
		rect = new Rectangle(150, 404, 12, 12);
		map.put(rect, 27);

		rect = new Rectangle(426, 436, 12, 12);
		map.put(rect, 28);		
		
		long startTime = System.currentTimeMillis();
		Quadtree quadtree = new Quadtree(0, new Rectangle(0, 0, 500, 500));
		quadtree.setMAX_LEVELS(5);
		quadtree.setMAX_OBJECTS(10);
		quadtree.clear();
		for (Rectangle rectangle : map.keySet()) {
			quadtree.insert(rectangle);
		}

		List<Rectangle> returnObjects = new ArrayList<>();
		returnObjects.clear();
		Rectangle tanke = new Rectangle(284, 270, 36, 36);
		quadtree.retrieve(returnObjects, tanke);
		long endTime = System.currentTimeMillis();
		System.out.println("花费了" + (endTime - startTime) + "ms");
		System.out.println("坦克信息：" + tanke.toString());
		for (Rectangle rectangle : returnObjects) {
			System.out.println(map.get(rectangle) + ":" + rectangle.toString());
		}
	}
}
