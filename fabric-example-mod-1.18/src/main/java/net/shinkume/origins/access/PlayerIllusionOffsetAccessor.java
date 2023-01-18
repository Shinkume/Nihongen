package net.shinkume.origins.access;

import net.minecraft.util.math.Vec3d;

public interface PlayerIllusionOffsetAccessor {

    public final Vec3d[][] clientsideillusionoffset = new Vec3d[2][4];

    public int clientSideIllusionTicks = 0;

    public Vec3d[] getIllusionOffsets(float f);

}

