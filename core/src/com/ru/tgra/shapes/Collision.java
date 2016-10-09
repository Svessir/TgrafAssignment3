package com.ru.tgra.shapes;

import java.util.Comparator;

public class Collision {
	public final Vector3D newTravelVector;
	public final float tHit;

	public static final Comparator<Collision> tHitComparator = new Comparator<Collision>() {
		@Override
		public int compare(Collision o1, Collision o2) {
			if(o1.tHit < o2.tHit)
				return -1;
			if(o1.tHit > o2.tHit)
				return 1;
			return 0;
		}
	};

	public Collision(Vector3D newTravelVector, float tHit) {
		this.newTravelVector = newTravelVector;
		this.tHit = tHit;
	}
}
