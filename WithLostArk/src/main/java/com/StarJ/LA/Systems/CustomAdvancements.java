package com.StarJ.LA.Systems;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.StarJ.LA.Core;
import com.StarJ.LA.Items.Items;
import com.StarJ.LA.Systems.Fishes.Biomes;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementDisplay;
import net.minecraft.advancements.AdvancementFrameType;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementTree;
import net.minecraft.advancements.Advancements;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionInstance;
import net.minecraft.advancements.critereon.LootSerializationContext;
import net.minecraft.commands.CustomFunction;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.AdvancementDataWorld;
import net.minecraft.server.level.EntityPlayer;

public class CustomAdvancements {
	private static final List<CustomAdvancements> list = new ArrayList<CustomAdvancements>();

	// 낚시
	public static CustomAdvancements fishing = new CustomAdvancements("fishing", Material.FISHING_ROD, "낚시 초입자",
			"낚시를 처음 접한 당신에게 주는 칭호\n당신은 낚시 초입자입니다.", "textures/block/blue_concrete.png", AdvancementFrameType.a, true,
			true, false, new Reward(100, Items.money.setMoney(BigInteger.valueOf(1000))), 1);
	public static CustomAdvancements fishing_biome = new CustomAdvancements("fishing_biome", fishing,
			Material.NETHER_STAR, "낚시의 지배자", "모든 종류의 바이옴에서 낚시에 성공하세요!", AdvancementFrameType.b, true, true, false,
			new Reward(1000, Items.money.setMoney(BigInteger.valueOf(50000))), Biomes.values().length);
	// 낚시 - SNOWY
	public static CustomAdvancements snowy_fish = new CustomAdvancements("snowmy_fish", fishing, Material.ICE,
			"설산의 지배자", "설산에서 물고기 500마리를 잡아보세요!", AdvancementFrameType.b, true, true, false,
			new Reward(100, Items.money.setMoney(BigInteger.valueOf(5000))), 500);
	public static CustomAdvancements snowy_fish_type = new CustomAdvancements("snowmy_fish_type", snowy_fish,
			Material.ICE, "설산의 낚시왕", "설산의 물고리를 모든 종류 잡아보세요!", AdvancementFrameType.c, true, true, false,
			new Reward(500, Items.money.setMoney(BigInteger.valueOf(10000))), Biomes.Snowy.getFishes().size());
	// 낚시 - COLD
	public static CustomAdvancements cold_fish = new CustomAdvancements("cold_fish", fishing, Material.SPRUCE_SAPLING,
			"얼어붙은 지배자", "매우 추운 지역에서 물고기 500마리를 잡아보세요!", AdvancementFrameType.b, true, true, false,
			new Reward(100, Items.money.setMoney(BigInteger.valueOf(5000))), 500);
	public static CustomAdvancements cold_fish_type = new CustomAdvancements("cold_fish_type", cold_fish,
			Material.SPRUCE_SAPLING, "얼어붙은 낚시왕", "매우 추운 지역의 물고리를 모든 종류 잡아보세요!", AdvancementFrameType.c, true, true,
			false, new Reward(500, Items.money.setMoney(BigInteger.valueOf(10000))), Biomes.Cold.getFishes().size());
	// 낚시 - TEMPERATE
	public static CustomAdvancements temperate_fish = new CustomAdvancements("temperate_fish", fishing,
			Material.SUNFLOWER, "온대 기후의 지배자", "온대 기후 지역에서 물고기 500마리를 잡아보세요!", AdvancementFrameType.b, true, true, false,
			new Reward(100, Items.money.setMoney(BigInteger.valueOf(5000))), 500);
	public static CustomAdvancements temperate_fish_type = new CustomAdvancements("temperate_fish_type", temperate_fish,
			Material.SUNFLOWER, "온대 기후의 낚시왕", "온대 기후 지역의 물고기를 모든 종류 잡아보세요!", AdvancementFrameType.c, true, true, false,
			new Reward(500, Items.money.setMoney(BigInteger.valueOf(10000))), Biomes.Temperate.getFishes().size());
	// 낚시 - WARM
	public static CustomAdvancements warm_fish = new CustomAdvancements("warm_fish", fishing, Material.SAND, "사막의 지배자",
			"사막 지역에서 물고기 500마리를 잡아보세요!", AdvancementFrameType.b, true, true, false,
			new Reward(100, Items.money.setMoney(BigInteger.valueOf(5000))), 500);
	public static CustomAdvancements warm_fish_type = new CustomAdvancements("warm_fish_type", warm_fish, Material.SAND,
			"사막의 낚시왕", "사막 지역의 물고기를 모든 종류 잡아보세요!", AdvancementFrameType.c, true, true, false,
			new Reward(500, Items.money.setMoney(BigInteger.valueOf(10000))), Biomes.Warm.getFishes().size());
	// 낚시 - AQUASTIC
	public static CustomAdvancements aquastic_fish = new CustomAdvancements("aquastic_fish", fishing,
			Material.PRISMARINE, "바다의 지배자", "바다 지역에서 물고기 500마리를 잡아보세요!", AdvancementFrameType.b, true, true, false,
			new Reward(100, Items.money.setMoney(BigInteger.valueOf(5000))), 500);
	public static CustomAdvancements aquastic_fish_type = new CustomAdvancements("aquastic_fish_type", aquastic_fish,
			Material.PRISMARINE, "바다의 낚시왕", "바다 지역의 물고기를 모든 종류 잡아보세요!", AdvancementFrameType.c, true, true, false,
			new Reward(500, Items.money.setMoney(BigInteger.valueOf(10000))), Biomes.Aquastic.getFishes().size());
	// 낚시 - CAVE
	public static CustomAdvancements cave_fish = new CustomAdvancements("cave_fish", fishing,
			Material.LARGE_AMETHYST_BUD, "동굴의 지배자", "동굴 지역에서 물고기 500마리를 잡아보세요!", AdvancementFrameType.b, true, true,
			false, new Reward(100, Items.money.setMoney(BigInteger.valueOf(5000))), 500);
	public static CustomAdvancements cave_fish_type = new CustomAdvancements("cave_fish_type", cave_fish,
			Material.LARGE_AMETHYST_BUD, "동굴의 낚시왕", "동굴 지역의 물고기를 모든 종류 잡아보세요!", AdvancementFrameType.c, true, true,
			false, new Reward(500, Items.money.setMoney(BigInteger.valueOf(10000))), Biomes.Cave.getFishes().size());
	// 낚시 - NETHER
	public static CustomAdvancements nether_fish = new CustomAdvancements("nether_fish", fishing, Material.NETHERRACK,
			"지옥의 지배자", "지옥 지역에서 물고기 500마리를 잡아보세요!", AdvancementFrameType.b, true, true, false,
			new Reward(100, Items.money.setMoney(BigInteger.valueOf(5000))), 500);
	public static CustomAdvancements nether_fish_type = new CustomAdvancements("nether_fish_type", nether_fish,
			Material.NETHERRACK, "지옥의 낚시왕", "지옥 지역의 물고기를 모든 종류 잡아보세요!", AdvancementFrameType.c, true, true, false,
			new Reward(500, Items.money.setMoney(BigInteger.valueOf(10000))), Biomes.Nether.getFishes().size());
	// 낚시 - END
	public static CustomAdvancements end_fish = new CustomAdvancements("end_fish", fishing, Material.END_STONE,
			"엔더의 지배자", "엔더 지역에서 물고기 500마리를 잡아보세요!", AdvancementFrameType.b, true, true, false,
			new Reward(100, Items.money.setMoney(BigInteger.valueOf(5000))), 500);
	public static CustomAdvancements end_fish_type = new CustomAdvancements("end_fish_type", end_fish,
			Material.END_STONE, "엔더의 낚시왕", "엔더 지역의 물고기를 모든 종류 잡아보세요!", AdvancementFrameType.c, true, true, false,
			new Reward(500, Items.money.setMoney(BigInteger.valueOf(10000))), Biomes.End.getFishes().size());
	//

	private static class Reward {
		private final int exp;
		private final ItemStack[] items;

		public Reward(ItemStack... items) {
			this.exp = 0;
			this.items = items;
		}

		public Reward(int exp, ItemStack... items) {
			this.exp = exp;
			this.items = items;
		}

		public void giveReward(Player player) {
			player.giveExp(this.exp);
			for (ItemStack i : items) {
				player.getWorld().dropItem(player.getLocation(), i);
			}
		}

	}

	public static CustomAdvancements valueOf(String key) {
		for (CustomAdvancements ua : values()) {
			if (ua.getKey().equals(key))
				return ua;
		}
		return null;
	}

	public static List<CustomAdvancements> values() {
		return list;
	}

	private final String key;
	private final MinecraftKey mine_key;
	private final CustomAdvancements parent;
	private final AdvancementDisplay display;
	private final AdvancementRewards advancement_reward;
	private final Advancement adv;
	private final String background;
	private final Reward reward;
	private final int condition;

	// 부모 X
	public CustomAdvancements(String key, Material icon, String title, String description, String background,
			AdvancementFrameType frame, boolean toast, boolean announce, boolean hidden, Reward reward, int condition) {
		this.key = key;
		this.mine_key = new MinecraftKey(Core.getCore().getName().toLowerCase(), key.toLowerCase());
		this.parent = null;
		this.background = background;
		this.display = new AdvancementDisplay(CraftItemStack.asNMSCopy(new ItemStack(icon)),
				IChatBaseComponent.a(title), IChatBaseComponent.a(description),
				new MinecraftKey("minecraft", background), frame, toast, announce, hidden);
		this.advancement_reward = new AdvancementRewards(0, new MinecraftKey[0], new MinecraftKey[0],
				new CustomFunction.a(mine_key));
		String[][] s = { { this.mine_key.toString() } };
		this.adv = new Advancement(this.mine_key, null, this.display, this.advancement_reward, getMap(), s);
		this.reward = reward;
		this.condition = condition;
		list.add(this);
	}

	// 부모 O
	public CustomAdvancements(String key, CustomAdvancements parent, Material icon, String title, String description,
			AdvancementFrameType frame, boolean toast, boolean announce, boolean hidden, Reward reward, int condition) {
		this.key = key;
		this.mine_key = new MinecraftKey(Core.getCore().getName().toLowerCase(), key.toLowerCase());
		this.parent = parent;
		this.background = parent.background;
		this.display = new AdvancementDisplay(CraftItemStack.asNMSCopy(new ItemStack(icon)),
				IChatBaseComponent.a(title), IChatBaseComponent.a(description),
				new MinecraftKey("minecraft", background), frame, toast, announce, hidden);
		this.advancement_reward = new AdvancementRewards(0, new MinecraftKey[0], new MinecraftKey[0],
				new CustomFunction.a(mine_key));
		String[][] s = { { this.mine_key.toString() } };
		this.adv = new Advancement(this.mine_key, this.parent.getAdvancement(), this.display, this.advancement_reward,
				getMap(), s);
		parent.getAdvancement().a(this.adv);
		this.reward = reward;
		this.condition = condition;
		list.add(this);
	}

	public String getKey() {
		return key;
	}

	public MinecraftKey getMinecraftkey() {
		return this.mine_key;
	}

	public CustomAdvancements getParents() {
		return this.parent;
	}

	public AdvancementDisplay getDisplay() {
		return this.display;
	}

	public AdvancementRewards getReawards() {
		return this.advancement_reward;
	}

	public boolean isDone(int now) {
		return this.condition <= now;
	}

	public Map<String, Criterion> getMap() {
		Map<String, Criterion> map = new HashMap<String, Criterion>();
		map.put(getMinecraftkey().toString(), new Criterion(null));

		new CriterionInstance() {
			public JsonObject a(LootSerializationContext context) {
				JsonObject obj = new JsonObject();
				obj.addProperty("isDone", false);
				return obj;
			};

			@Override
			public MinecraftKey a() {
				return new MinecraftKey("minecraft", "impossible");
			}
		};
		return map;
	}

	public Advancement getAdvancement() {
		return this.adv;
	}

	public static void ResetAdvancements() {
		// 바닐라 삭제 및 업적 등록
		for (Player player : Bukkit.getOnlinePlayers()) {
			RemovePlayerVanilaData(player);
		}
		Advancements advancements = new Advancements();
		Map<MinecraftKey, Advancement.SerializedAdvancement> map = Maps.newHashMap();
		World world = Bukkit.getWorlds().get(0);
		AdvancementDataWorld adv = (((CraftWorld) world).getHandle()).n().ax();
		advancements.a(map);
		Iterator<Advancement> iterator = advancements.b().iterator();
		while (iterator.hasNext()) {
			Advancement advancement = iterator.next();
			if (advancement.c() != null)
				AdvancementTree.a(advancement);
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			EntityPlayer p = ((CraftPlayer) player).getHandle();
			p.M().a(adv);
		}

		adv.c = advancements;
	}

	private static void RemovePlayerVanilaData(Player player) {
		Iterator<org.bukkit.advancement.Advancement> itr = Bukkit.advancementIterator();
		while (itr.hasNext()) {
			org.bukkit.advancement.Advancement adv = itr.next();
			org.bukkit.advancement.AdvancementProgress pro = player.getAdvancementProgress(adv);
			for (String c : pro.getAwardedCriteria()) {
//				Bukkit.broadcastMessage(c);
				pro.revokeCriteria(c);
			}
		}
		EntityPlayer p = ((CraftPlayer) player).getHandle();
		p.M().a();
	}

	public static void Registry() {
		// intial 명령어
		World world = Bukkit.getWorlds().get(0);
		AdvancementDataWorld adv = (((CraftWorld) world).getHandle()).n().ax();
		Advancements advancements = adv.c;
		Map<MinecraftKey, Advancement.SerializedAdvancement> map = Maps.newHashMap();
		map.putAll(getRegistMaps());
		advancements.a(map);
		Iterator<Advancement> iterator = advancements.b().iterator();
		while (iterator.hasNext()) {
			Advancement advancement = iterator.next();
			if (advancement.c() != null)
				AdvancementTree.a(advancement);
		}
		adv.c = advancements;
	}

	private static Map<MinecraftKey, Advancement.SerializedAdvancement> getRegistMaps() {
		Map<MinecraftKey, Advancement.SerializedAdvancement> map = Maps.newHashMap();
		for (CustomAdvancements cad : values()) {
			Advancement ad = cad.getAdvancement();
			map.put(cad.getMinecraftkey(), ad.a());
		}
		return map;
	}

	public void giveRewards(Player player) {
		reward.giveReward(player);
	}

	public void grantAndReward(Player player) {
		grantAndReward(player, this);
	}

	public void grant(Player player) {
		grant(player, this);
	}

	public static void grantAndReward(Player player, CustomAdvancements cad) {
		// 업적을 플레이어에게 주는 명령어
		EntityPlayer p = ((CraftPlayer) player).getHandle();
		Advancement ad = cad.getAdvancement();
		net.minecraft.advancements.AdvancementProgress progress = p.M().b(ad);
		if (!progress.a())
			p.M().a(ad, cad.getMinecraftkey().toString());
		cad.giveRewards(player);
	}

	public static void grant(Player player, CustomAdvancements cad) {
		// 업적을 플레이어에게 주는 명령어
		EntityPlayer p = ((CraftPlayer) player).getHandle();
		Advancement ad = cad.getAdvancement();
		net.minecraft.advancements.AdvancementProgress progress = p.M().b(ad);
		if (!progress.a())
			p.M().a(ad, cad.getMinecraftkey().toString());
	}

	public boolean has(Player player) {
		return has(player, this);
	}

	public static boolean has(Player player, CustomAdvancements cad) {
		// 업적을 플레이어에게 주는 명령어
		EntityPlayer p = ((CraftPlayer) player).getHandle();
		Advancement ad = cad.getAdvancement();
		net.minecraft.advancements.AdvancementProgress progress = p.M().b(ad);
		return progress.a();

	}

}
