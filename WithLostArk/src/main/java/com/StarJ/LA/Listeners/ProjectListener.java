package com.StarJ.LA.Listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.MetadataValue;

import com.StarJ.LA.Core;
import com.StarJ.LA.Items.Items;
import com.StarJ.LA.Items.Projectileable;

public class ProjectListener implements Listener {
	@EventHandler
	public void Events(ProjectileHitEvent e) {
		Projectile pro = e.getEntity();
		if (pro.getType().equals(EntityType.SNOWBALL)) {
			if (e.getHitEntity() != null && e.getHitEntity() instanceof LivingEntity)
				((LivingEntity) e.getHitEntity()).damage(0.1d);
		}
		if (pro.hasMetadata("key"))
			for (MetadataValue v : pro.getMetadata("key"))
				if (v.getOwningPlugin().equals(Core.getCore())) {
					String key = v.asString();
					Items i = Items.valueOf(key);
					if (i != null && i instanceof Projectileable) {
						Projectileable proa = (Projectileable) i;
						if (e.getHitEntity() != null)
							proa.Hit(pro, e.getHitEntity());
						if (e.getHitBlock() != null)
							proa.Hit(pro, e.getHitBlock());
					}
				}
	}

	@EventHandler
	public void Events(ProjectileLaunchEvent e) {
	}

}
