package me.Mammothskier.Giants.entity.nms.v1_7_R3;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_7_R3.util.UnsafeList;

import net.minecraft.server.v1_7_R3.EntityGiantZombie;
import net.minecraft.server.v1_7_R3.EntityHuman;
import net.minecraft.server.v1_7_R3.EntityIronGolem;
import net.minecraft.server.v1_7_R3.EntitySkeleton;
import net.minecraft.server.v1_7_R3.EntityVillager;
import net.minecraft.server.v1_7_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_7_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_7_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_7_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_7_R3.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_7_R3.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_7_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_7_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_7_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_7_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_7_R3.World;
import net.minecraft.server.v1_7_R3.GenericAttributes;

public class CustomEntityGiantZombie extends EntityGiantZombie {

	public CustomEntityGiantZombie(World world) {
		super(world);
		try {
			Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
			bField.setAccessible(true);
			Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
			cField.setAccessible(true);
			bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
			cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
			this.n();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
		this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntitySkeleton.class, 1.0D, true));
		this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
		this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
		this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
		this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
		this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
	}
	
	@Override
	public float getHeadHeight() {
        return 14.440001F;
    }
	
	@Override
    protected void aC() {
        super.aW();
        this.getAttributeInstance(GenericAttributes.b).setValue(35D);
        this.getAttributeInstance(GenericAttributes.a).setValue(100.0D);
        this.getAttributeInstance(GenericAttributes.d).setValue(0.5D);
        this.getAttributeInstance(GenericAttributes.e).setValue(50.0D);
    }
	
	protected void n() {
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityVillager.class, 1, true));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityIronGolem.class, 1, true));
        this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, 0, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityIronGolem.class, 0, true));
    }
}
