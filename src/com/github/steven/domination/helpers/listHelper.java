package com.github.steven.domination.helpers;

import com.github.steven.domination.datatypes.Cuboid;

import java.util.List;
import java.util.Random;

public final class listHelper {
    public static Cuboid getRandomCuboid(List<Cuboid> list)
    {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
