package net.minecraft.pathfinding;

public enum PathNodeType {
   BLOCKED(-1.0F),
   OPEN(0.0F),
   WALKABLE(0.0F),
   TRAPDOOR(0.0F),
   FENCE(-1.0F),
   LAVA(-1.0F),
   WATER(8.0F),
   RAIL(0.0F),
   DANGER_FIRE(8.0F),
   DAMAGE_FIRE(16.0F),
   DANGER_CACTUS(8.0F),
   DAMAGE_CACTUS(-1.0F),
   DANGER_OTHER(8.0F),
   DAMAGE_OTHER(-1.0F),
   DOOR_OPEN(0.0F),
   DOOR_WOOD_CLOSED(-1.0F),
   DOOR_IRON_CLOSED(-1.0F);

   private final float field_186307_r;

   private PathNodeType(float p_i46653_3_) {
      this.field_186307_r = p_i46653_3_;
   }

   public float func_186289_a() {
      return this.field_186307_r;
   }
}
