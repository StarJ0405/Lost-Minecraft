package com.StarJ.LA.Systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.StarJ.LA.Systems.Fishes.Rarity;

public enum Basics {
	Farming("농사", Material.IRON_HOE) {
		@Override
		public int getNeedEXP(int level) {
			return level * level * 200;
		}

		@Override
		public double getChance(int level) {
			return level * 0.01;
		}

		@Override
		public List<String> getLore(int level) {
			List<String> lore = new ArrayList<String>();
			if (level >= 40) {
				lore.add(ChatColor.GREEN + "채집의 왕" + ChatColor.AQUA + " (쉬프트+쉬프트 / " + (450 - level * 3) + "초)");
				lore.add(ChatColor.WHITE + " - " + (8 + level / 5) + "칸 내에 있는 농사물을 채집합니다.");
			}
			if (level >= 20) {
				lore.add(ChatColor.GREEN + "빠른 수거" + ChatColor.AQUA + " (쉬프트+우클릭 / " + (300 - level) + "초)");
				lore.add(ChatColor.WHITE + " - " + (9 + level / 10 * 9) + "초 범위 내에 바닥에 떨어진 농사물을 수거합니다.");
			}
			if (level >= 10) {
				lore.add(ChatColor.GREEN + "비료 뿌리기" + ChatColor.AQUA + " (우클릭 / " + 5 + "초)");
				lore.add(ChatColor.WHITE + " - 비료를 뿌려 " + (level / 10 + 4) + "칸 내의 작물들을 " + getChance(level) * 100
						+ "%로 성장 시킵니다.");
			}
			lore.add(ChatColor.GREEN + "범위 수확" + ChatColor.AQUA + " (패시브 / 확정)");
			lore.add(ChatColor.WHITE + " - 괭이를 들고 수확시 수확 범위가 " + (level / 10 + 4) + "칸 증가합니다.");
			return lore;
		}
	},
	Fishing("낚시", Material.FISHING_ROD) {
		@Override
		public int getNeedEXP(int level) {
			return level * level * 10;
		}

		@Override
		public double getChance(int level) {
			return level * 0.005;
		}

		@Override
		public List<String> getLore(int level) {
			List<String> lore = new ArrayList<String>();
			if (level >= 40) {
				lore.add(ChatColor.GREEN + "낚시의 신" + ChatColor.AQUA + " (쉬프트+쉬프트 / " + (450 - level * 3) + "초)");
				lore.add(ChatColor.WHITE + " - 다음 " + (level / 10 + 1) + "회동안 RARE이하는 등장하지 않습니다.");
			}
			if (level >= 20) {
				lore.add(ChatColor.GREEN + "대어의 감" + ChatColor.AQUA + " (쉬프트+좌클릭 / " + (300 - level * 2) + "초)");
				lore.add(ChatColor.WHITE + " - 다음 " + (level / 10 + 1) + "회 높은 물고기가 " + (level * 0.5) + "% 잘뜹니다.");
			}
			if (level >= 10) {
				lore.add(ChatColor.GREEN + "손맛" + ChatColor.AQUA + " (좌클릭 / " + (100 - level) + "초)");
				lore.add(ChatColor.WHITE + " - 다음 " + (level / 10 + 1) + "회 좋은 물고기가 " + (level * 0.1) + "% 잘뜹니다.");
			}
			lore.add(ChatColor.GREEN + "어부지리" + ChatColor.AQUA + " (패시브 / 확률)");
			lore.add(ChatColor.WHITE + " - 물고기를 낚을 때 " + getChance(level) * 100 + "%로 " + (level / 10 + 1)
					+ "마리 획득합니다.");
			return lore;
		}
	},
	Hunting("사냥", Material.BOW) {
		@Override
		public int getNeedEXP(int level) {
			return level * level * 10;
		}

		@Override
		public double getChance(int level) {
			return level * 0.002;
		}

		@Override
		public List<String> getLore(int level) {
			List<String> lore = new ArrayList<String>();
			if (level >= 40) {
				lore.add(ChatColor.GREEN + "깔끔한 도축가" + ChatColor.AQUA + " (쉬프트+쉬프트 / " + (450 - level * 3) + "초)");
				lore.add(ChatColor.WHITE + " - " + (level / 2) + "초 동안 사냥감을 죽이지 않고 도축을 합니다.");
			}
			if (level >= 20) {
				lore.add(ChatColor.GREEN + "번식" + ChatColor.AQUA + " (쉬프트+우클릭 / " + (300 - level) + "초)");
				lore.add(ChatColor.WHITE + " - 해당 동물을 " + (level / 10 + 1) + "마리 번식 시킵니다.");
			}
			if (level >= 10) {
				lore.add(ChatColor.GREEN + "사냥의 눈" + ChatColor.AQUA + " (우클릭 / " + (100 - level) + "초)");
				lore.add(ChatColor.WHITE + " - " + (level / 2 + 5) + "칸 내에 동물들의 위치를 밝힙니다.");
			}
			lore.add(ChatColor.GREEN + "훌륭한 사냥감" + ChatColor.AQUA + " (패시브 / 확정)");
			lore.add(ChatColor.WHITE + " - " + (getChance(level) * 100) + "%로 " + (level / 5 + 2) + "개 추가 획득합니다.");

			return lore;
		}
	},
	Mining("광질", Material.IRON_PICKAXE) {
		@Override
		public int getNeedEXP(int level) {
			return level * level * 20;
		}

		@Override
		public double getChance(int level) {
			return level * 0.002;
		}

		@Override
		public List<String> getLore(int level) {
			List<String> lore = new ArrayList<String>();
			if (level >= 40) {
				lore.add(ChatColor.GREEN + "X-Ray" + ChatColor.AQUA + " (쉬프트+쉬프트 / " + (450 - level * 3) + "초)");
				lore.add(ChatColor.WHITE + " - " + (5 + level / 5) + "칸 내에 광물의 위치를 발견합니다.");
			}
			if (level >= 20) {
				lore.add(ChatColor.GREEN + "피버 타임" + ChatColor.AQUA + " (쉬프트+우클릭 / " + (300 - level) + "초)");
				lore.add(ChatColor.WHITE + " - " + (level * 2) + "초 동안 블록을 즉시 파괴합니다.");
			}
			if (level >= 10) {
				lore.add(ChatColor.GREEN + "숙련된 곡괭이질" + ChatColor.AQUA + " (우클릭 / " + (60 - level) * 5 + "초)");
				lore.add(ChatColor.WHITE + " - 성급함 " + (level / 10) + "를 " + level * 5 + "초 동안 획득합니다.");
			}
			lore.add(ChatColor.GREEN + "행운의 사나이" + ChatColor.AQUA + " (패시브 / 확률)");
			lore.add(ChatColor.WHITE + " - " + (getChance(level) * 100) + "%로 " + (level / 10 + 1) + "개 추가 획득합니다.");
			return lore;
		}
	},
	Chopping("벌목", Material.IRON_AXE) {
		@Override
		public int getNeedEXP(int level) {
			return level * level * 100;
		}

		@Override
		public double getChance(int level) {
			return level * 0.01;
		}

		@Override
		public List<String> getLore(int level) {
			List<String> lore = new ArrayList<String>();
			if (level >= 40) {
				lore.add(ChatColor.GREEN + "포츈타임" + ChatColor.AQUA + " (쉬프트+쉬프트 / " + (450 - level * 3) + "초)");
				lore.add(ChatColor.WHITE + " - " + (5 + level / 5) + "칸 내에 광물의 위치를 발견합니다.");
			}
			if (level >= 20) {
				lore.add(ChatColor.GREEN + "나무 성장" + ChatColor.AQUA + " (쉬프트+우클릭 / " + (300 - level * 2) + "초)");
				lore.add(ChatColor.WHITE + " - " + (level / 2 + 5) + "범위내 묘목을 전부 성장시킵니다.");
			}
			if (level >= 10) {
				lore.add(ChatColor.GREEN + "진심 베기" + ChatColor.AQUA + " (우클릭 / " + (60 - level) * 5 + "초)");
				lore.add(ChatColor.WHITE + " - 주변 " + (level / 2 + 5) + "범위 나무와 나뭇잎을 베어버립니다.");
			}
			lore.add(ChatColor.GREEN + "묘목 심기" + ChatColor.AQUA + " (패시브 / 확률)");
			lore.add(ChatColor.WHITE + " - " + (getChance(level) * 100) + "%로 해당 자리에 묘목을 심습니다.");
			return lore;
		}
	},
	Digging("고고학", Material.IRON_SHOVEL) {
		@Override
		public int getNeedEXP(int level) {
			return level * level * 50;
		}

		@Override
		public double getChance(int level) {
			return level * 0.001;
		}

		@Override
		public List<String> getLore(int level) {
			List<String> lore = new ArrayList<String>();
			if (level >= 40) {
				lore.add(ChatColor.GREEN + "완벽한 발굴" + ChatColor.AQUA + " (쉬프트+쉬프트 / " + (450 - level * 3) + "초)");
				lore.add(ChatColor.WHITE + " - " + (level / 10) + "회동안 보물을 발굴합니다.");
			}
			if (level >= 20) {
				lore.add(ChatColor.GREEN + "과감한 발굴" + ChatColor.AQUA + " (쉬프트+우클릭 / " + (300 - level * 2) + "초)");
				lore.add(ChatColor.WHITE + " - " + (level * 2) + "초동안 보물 확률이 " + getChance(level) * 100 + "% 증가합니다.");
			}
			if (level >= 10) {
				lore.add(ChatColor.GREEN + "노련한 탐색" + ChatColor.AQUA + " (우클릭 / " + (60 - level) * 5 + "초)");
				lore.add(ChatColor.WHITE + " - " + (level * 2) + "초동안 발굴범위가 " + (level / 10 + 3) + "칸 증가합니다.");
			}
			lore.add(ChatColor.GREEN + "보물 탐색" + ChatColor.AQUA + " (패시브 / 확률)");
			lore.add(ChatColor.WHITE + " - " + (getChance(level) * 100) + "%로 보물을 발견합니다.");
			return lore;
		}
	},
	Cooking("요리", Material.CAMPFIRE) {
		@Override
		public int getNeedEXP(int level) {
			return level * level * 5;
		}

		@Override
		public double getChance(int level) {
			return level * 0.02;
		}

		@Override
		public List<String> getLore(int level) {
			List<String> lore = new ArrayList<String>();
			if (level >= 40) {
				lore.add(ChatColor.GREEN + "절약정신" + ChatColor.AQUA + " (패시브 / 확률)");
				lore.add(ChatColor.WHITE + " - 각각 " + getChance(level) * 100 / 5 + "%로 요리재료를 돌려받습니다.");
			}
			if (level >= 20) {
				lore.add(ChatColor.GREEN + "달인" + ChatColor.AQUA + " (패시브 / 확률)");
				lore.add(ChatColor.WHITE + " - " + getChance(level) * 100 / 4 + "%로 요리 등급이 상승합니다.");
			}
			if (level >= 10) {
				lore.add(ChatColor.GREEN + "고급 요리사" + ChatColor.AQUA + " (패시브 / 확률)");
				lore.add(ChatColor.WHITE + " - " + getChance(level) * 100 / 2 + "%로 요리 효과가 " + level * 2 + "% 증가합니다.");
			}
			lore.add(ChatColor.GREEN + "특별 요리" + ChatColor.AQUA + " (패시브 / 확률)");
			lore.add(ChatColor.WHITE + " - " + (getChance(level) * 100) + "%로 추가효과가 붙습니다.");
			return lore;
		}
	},
	Potioning("양조", Material.POTION) {
		@Override
		public int getNeedEXP(int level) {
			return level * level * 5;
		}

		@Override
		public double getChance(int level) {
			return level * 0.02;
		}

		@Override
		public List<String> getLore(int level) {
			List<String> lore = new ArrayList<String>();
			if (level >= 40) {
				lore.add(ChatColor.GREEN + "증식" + ChatColor.AQUA + " (패시브 / 확률)");
				lore.add(ChatColor.WHITE + " - " + getChance(level) * 100 / 5 + "%로 물약을 " + (level / 16)
						+ "개 추가 획득합니다..");
			}
			if (level >= 20) {
				lore.add(ChatColor.GREEN + "의도치않은 성공" + ChatColor.AQUA + " (패시브 / 확률)");
				lore.add(ChatColor.WHITE + " - " + getChance(level) * 100 / 4 + "%로 포션에 추가효과가 붙습니다.");
			}
			if (level >= 10) {
				lore.add(ChatColor.GREEN + "재활용" + ChatColor.AQUA + " (패시브 / 확률)");
				lore.add(ChatColor.WHITE + " - " + getChance(level) * 100 / 2 + "%로 재료를 소모하지 않습니다.");
			}
			lore.add(ChatColor.GREEN + "강력한 포션" + ChatColor.AQUA + " (패시브 / 확률)");
			lore.add(ChatColor.WHITE + " - " + (getChance(level) * 100) + "%로 포션효과가 " + level * 2 + "% 강해집니다.");
			return lore;
		}
	}

	//
	;

	//
	private static HashMap<Material, ItemStack[]> farming = new HashMap<Material, ItemStack[]>();
	private static HashMap<Material, Integer> farming_exp = new HashMap<Material, Integer>();
	private static HashMap<Material, Byte> farming_data = new HashMap<Material, Byte>();
	private static HashMap<Rarity, Integer> fishing_exp = new HashMap<Rarity, Integer>();
	private static HashMap<EntityType, ItemStack[]> hunting = new HashMap<EntityType, ItemStack[]>();
	private static HashMap<EntityType, Integer> hunting_exp = new HashMap<EntityType, Integer>();
	private static HashMap<Material, Material> mining = new HashMap<Material, Material>();
	private static HashMap<Material, Integer> mining_exp = new HashMap<Material, Integer>();
	private static HashMap<Material, Material> chopping = new HashMap<Material, Material>();
	private static HashMap<Material, Integer> chopping_exp = new HashMap<Material, Integer>();
	private static HashMap<Material, Material> digging = new HashMap<Material, Material>();
	private static HashMap<Material, Integer> digging_exp = new HashMap<Material, Integer>();
	private static HashMap<Material, Material> cooking = new HashMap<Material, Material>();
	private static HashMap<Material, Integer> cooking_exp = new HashMap<Material, Integer>();
	private static HashMap<Material, Material> potioning = new HashMap<Material, Material>();
	private static HashMap<Material, Integer> potioning_exp = new HashMap<Material, Integer>();
	//
	private final String Displyname;
	private final Material icon;

	private Basics(String Displyname, Material icon) {
		this.Displyname = Displyname;
		this.icon = icon;
	}

	public String getDisplyname() {
		return Displyname;
	}

	public int getEXP(Player player) {
		return ConfigStore.getBasicsEXP(player, this);
	}

	public int getLevel(Player player) {
		return ConfigStore.getBasicsLevel(player, this);
	}

	public abstract double getChance(int level);

	public abstract int getNeedEXP(int level);

	public abstract List<String> getLore(int level);

	public int getMaxLevel() {
		return 50;
	}

	public Material getIcon() {
		return icon;
	}

	public boolean isActive(int level) {
		return new Random().nextDouble() < getChance(level);
	}

	public void addEXP(Player player, int exp) {
		int level = getLevel(player);
		exp += getEXP(player);
		int need = getNeedEXP(level);
		if (exp >= need) {
			exp -= need;
			level++;

		}
		ConfigStore.setBasicsLevel(player, this, level);
		ConfigStore.setBasicsEXP(player, this, exp);
	}

	// 농사
	public static boolean isFarming(Material type) {
		return farming.containsKey(type);
	}

	public static ItemStack[] getFarming(Material type) {
		return farming.containsKey(type) ? farming.get(type) : new ItemStack[0];
	}

	public static int getFarmingExp(Material type) {
		return farming_exp.containsKey(type) ? farming_exp.get(type) : 0;
	}

	public static byte getFarmingData(Material type) {
		return farming_data.containsKey(type) ? farming_data.get(type) : (byte) 0;
	}

	// 낚시
	public static int getFishingExp(Rarity rare) {
		return fishing_exp.containsKey(rare) ? fishing_exp.get(rare) : 0;
	}

	// 사냥
	public static boolean isHunting(EntityType type) {
		return hunting.containsKey(type);
	}

	public static ItemStack[] getHunting(EntityType type) {
		return hunting.containsKey(type) ? hunting.get(type) : new ItemStack[0];
	}

	public static int getHuntingExp(EntityType type) {
		return hunting_exp.containsKey(type) ? hunting_exp.get(type) : 0;
	}

	// 광질
	public static boolean isMining(Material type) {
		return mining.containsKey(type);
	}

	public static Material getMining(Material type) {
		return mining.containsKey(type) ? mining.get(type) : Material.AIR;
	}

	public static int getMiningExp(Material type) {
		return mining_exp.containsKey(type) ? mining_exp.get(type) : 0;
	}

	// 벌목
	public static boolean isChopping(Material type) {
		return chopping.containsKey(type);
	}

	public static Material getChopping(Material type) {
		return chopping.containsKey(type) ? chopping.get(type) : Material.AIR;
	}

	public static int getChoppingExp(Material type) {
		return chopping_exp.containsKey(type) ? chopping_exp.get(type) : 0;
	}

	// 고고학
	public static boolean isDigging(Material type) {
		return digging.containsKey(type);
	}

	public static Material getDigging(Material type) {
		return digging.containsKey(type) ? digging.get(type) : Material.AIR;
	}

	public static int getDiggingExp(Material type) {
		return digging_exp.containsKey(type) ? digging_exp.get(type) : 0;
	}

	// 요리
	public static boolean isCooking(Material type) {
		return cooking.containsKey(type);
	}

	public static Material getCooking(Material type) {
		return cooking.containsKey(type) ? cooking.get(type) : Material.AIR;
	}

	public static int getCookingExp(Material type) {
		return cooking_exp.containsKey(type) ? cooking_exp.get(type) : 0;
	}

	// 양조
	public static boolean isPotioning(Material type) {
		return potioning.containsKey(type);
	}

	public static Material getPotioning(Material type) {
		return potioning.containsKey(type) ? potioning.get(type) : Material.AIR;
	}

	public static int getPotioningExp(Material type) {
		return potioning_exp.containsKey(type) ? potioning_exp.get(type) : 0;
	}

	@SuppressWarnings("deprecation")
	public static void initial() {
		// 농사
		farming.put(Material.WHEAT, new ItemStack[] { new ItemStack(Material.WHEAT, 1 + 1, (byte) 0),
				new ItemStack(Material.WHEAT_SEEDS, 1 + 3, (short) 0) });
		farming_exp.put(Material.WHEAT, 3);
		farming_data.put(Material.WHEAT, (byte) 7);

		farming.put(Material.POTATOES, new ItemStack[] { new ItemStack(Material.POTATOES, 1 + 4, (short) 1),
				new ItemStack(Material.POISONOUS_POTATO, 1 + 1, (short) 0) });
		farming_exp.put(Material.POTATOES, 3);
		farming_data.put(Material.POTATOES, (byte) 7);

		farming.put(Material.CARROTS, new ItemStack[] { new ItemStack(Material.CARROTS, 1 + 3, (short) 2) });
		farming_exp.put(Material.CARROTS, 3);
		farming_data.put(Material.CARROTS, (byte) 7);

		farming.put(Material.BEETROOTS, new ItemStack[] { new ItemStack(Material.BEETROOTS, 1 + 0, (short) 1),
				new ItemStack(Material.BEETROOT_SEEDS, 1 + 3, (short) 1) });
		farming_exp.put(Material.BEETROOTS, 2);
		farming_data.put(Material.BEETROOTS, (byte) 3);

		farming.put(Material.PUMPKIN, new ItemStack[] { new ItemStack(Material.PUMPKIN, 1 + 0, (short) 1) });
		farming_exp.put(Material.PUMPKIN, 4);

		farming.put(Material.MELON, new ItemStack[] { new ItemStack(Material.MELON_SLICE, 1 + 4, (short) 3) });
		farming_exp.put(Material.MELON, 4);

		farming.put(Material.COCOA, new ItemStack[] { new ItemStack(Material.COCOA_BEANS, 1 + 1, (short) 2) });
		farming_exp.put(Material.COCOA, 1);
		farming_data.put(Material.COCOA, (byte) 2);

		farming.put(Material.SUGAR_CANE, new ItemStack[] { new ItemStack(Material.SUGAR_CANE, 1 + 0, (short) 0) });
		farming_exp.put(Material.SUGAR_CANE, 1);
		
		// 낚시
		fishing_exp.put(Rarity.Trash, 1);
		fishing_exp.put(Rarity.Common, 2);
		fishing_exp.put(Rarity.Uncommon, 4);
		fishing_exp.put(Rarity.Rare, 8);
		fishing_exp.put(Rarity.Epic, 16);
		fishing_exp.put(Rarity.Treasure, 32);
		fishing_exp.put(Rarity.Ominous, 64);

		// 사냥 - 수동
		hunting.put(EntityType.AXOLOTL, new ItemStack[] {});
		hunting_exp.put(EntityType.AXOLOTL, 1);

		hunting.put(EntityType.BAT, new ItemStack[] {});
		hunting_exp.put(EntityType.BAT, 1);

		hunting.put(EntityType.CAT, new ItemStack[] { new ItemStack(Material.STRING, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.CAT, 1);

		hunting.put(EntityType.CHICKEN, new ItemStack[] { new ItemStack(Material.FEATHER, 1 + 2, (short) 0),
				new ItemStack(Material.CHICKEN, 1 + 0, (short) 1) });
		hunting_exp.put(EntityType.CHICKEN, 1);

		hunting.put(EntityType.COD, new ItemStack[] { new ItemStack(Material.COD, 1 + 0, (short) 1),
				new ItemStack(Material.BONE_MEAL, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.COD, 1);

		hunting.put(EntityType.COW, new ItemStack[] { new ItemStack(Material.LEATHER, 1 + 2, (short) 0),
				new ItemStack(Material.BEEF, 1 + 2, (short) 1) });
		hunting_exp.put(EntityType.COW, 1);

		hunting.put(EntityType.DONKEY, new ItemStack[] { new ItemStack(Material.LEATHER, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.DONKEY, 1);

		hunting.put(EntityType.FOX, new ItemStack[] { new ItemStack(Material.EMERALD, 1 + 1, (short) 0),
				new ItemStack(Material.RABBIT_FOOT, 1 + 1, (short) 0),
				new ItemStack(Material.RABBIT_HIDE, 1 + 1, (short) 0), new ItemStack(Material.EGG, 1 + 1, (short) 0),
				new ItemStack(Material.WHEAT, 1 + 1, (short) 0), new ItemStack(Material.LEATHER, 1 + 1, (short) 0),
				new ItemStack(Material.FEATHER, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.FOX, 1);

		hunting.put(EntityType.GLOW_SQUID, new ItemStack[] { new ItemStack(Material.GLOW_INK_SAC, 1 + 2, (short) 1) });
		hunting_exp.put(EntityType.GLOW_SQUID, 1);

		hunting.put(EntityType.HORSE, new ItemStack[] { new ItemStack(Material.LEATHER, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.HORSE, 1);

		hunting.put(EntityType.MUSHROOM_COW, new ItemStack[] { new ItemStack(Material.LEATHER, 1 + 2, (short) 0),
				new ItemStack(Material.BEEF, 1 + 2, (short) 1) });
		hunting_exp.put(EntityType.MUSHROOM_COW, 1);

		hunting.put(EntityType.MULE, new ItemStack[] { new ItemStack(Material.LEATHER, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.MULE, 1);

		hunting.put(EntityType.OCELOT, new ItemStack[] {});
		hunting_exp.put(EntityType.OCELOT, 1);

		hunting.put(EntityType.PARROT, new ItemStack[] { new ItemStack(Material.FEATHER, 1 + 1, (short) 1) });
		hunting_exp.put(EntityType.PARROT, 1);

		hunting.put(EntityType.PIG, new ItemStack[] { new ItemStack(Material.PORKCHOP, 1 + 2, (short) 1) });
		hunting_exp.put(EntityType.PIG, 1);

		hunting.put(EntityType.PUFFERFISH, new ItemStack[] { new ItemStack(Material.PUFFERFISH, 1 + 0, (short) 1),
				new ItemStack(Material.BONE_MEAL, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.PUFFERFISH, 1);

		hunting.put(EntityType.RABBIT,
				new ItemStack[] { new ItemStack(Material.RABBIT_HIDE, 1 + 1, (short) 0),
						new ItemStack(Material.RABBIT, 1 + 1, (short) 0),
						new ItemStack(Material.RABBIT_FOOT, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.RABBIT, 1);

		hunting.put(EntityType.SALMON, new ItemStack[] { new ItemStack(Material.SALMON, 1 + 0, (short) 1),
				new ItemStack(Material.BONE_MEAL, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.SALMON, 1);

		hunting.put(EntityType.SHEEP,
				new ItemStack[] { new ItemStack(Material.BLACK_WOOL, 1 + 1, (short) 0),
						new ItemStack(Material.BLUE_WOOL, 1 + 1, (short) 0),
						new ItemStack(Material.BROWN_WOOL, 1 + 1, (short) 0),
						new ItemStack(Material.CYAN_WOOL, 1 + 1, (short) 0),
						new ItemStack(Material.GRAY_WOOL, 1 + 1, (short) 0),
						new ItemStack(Material.GREEN_WOOL, 1 + 1, (short) 0),
						new ItemStack(Material.LIGHT_BLUE_WOOL, 1 + 1, (short) 0),
						new ItemStack(Material.LIGHT_GRAY_WOOL, 1 + 1, (short) 0),
						new ItemStack(Material.LIME_WOOL, 1 + 1, (short) 0),
						new ItemStack(Material.MAGENTA_WOOL, 1 + 1, (short) 0),
						new ItemStack(Material.ORANGE_WOOL, 1 + 1, (short) 0),
						new ItemStack(Material.PINK_WOOL, 1 + 1, (short) 0),
						new ItemStack(Material.PURPLE_WOOL, 1 + 1, (short) 0),
						new ItemStack(Material.RED_WOOL, 1 + 1, (short) 0),
						new ItemStack(Material.WHITE_WOOL, 1 + 1, (short) 0),
						new ItemStack(Material.YELLOW_WOOL, 1 + 1, (short) 0),
						new ItemStack(Material.MUTTON, 1 + 1, (short) 1) });
		hunting_exp.put(EntityType.SHEEP, 1);

		hunting.put(EntityType.SKELETON_HORSE, new ItemStack[] { new ItemStack(Material.BONE, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.SKELETON_HORSE, 1);

		hunting.put(EntityType.SNOWMAN, new ItemStack[] { new ItemStack(Material.SNOWBALL, 1 + 15, (short) 0) });
		hunting_exp.put(EntityType.SNOWMAN, 1);

		hunting.put(EntityType.SQUID, new ItemStack[] { new ItemStack(Material.INK_SAC, 1 + 2, (short) 1) });
		hunting_exp.put(EntityType.SQUID, 1);

		hunting.put(EntityType.STRIDER, new ItemStack[] { new ItemStack(Material.STRING, 1 + 3, (short) 2) });
		hunting_exp.put(EntityType.STRIDER, 1);

		hunting.put(EntityType.TROPICAL_FISH, new ItemStack[] { new ItemStack(Material.TROPICAL_FISH, 1 + 0, (short) 1),
				new ItemStack(Material.BONE_MEAL, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.TROPICAL_FISH, 1);

		hunting.put(EntityType.TURTLE, new ItemStack[] { new ItemStack(Material.SEAGRASS, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.TURTLE, 1);

		hunting.put(EntityType.VILLAGER, new ItemStack[] {});
		hunting_exp.put(EntityType.VILLAGER, 1);

		hunting.put(EntityType.WANDERING_TRADER, new ItemStack[] {});
		hunting_exp.put(EntityType.WANDERING_TRADER, 1);
		// 사냥 - 중립
		hunting.put(EntityType.BEE, new ItemStack[] {});
		hunting_exp.put(EntityType.BEE, 1);

		hunting.put(EntityType.CAVE_SPIDER, new ItemStack[] { new ItemStack(Material.STRING, 1 + 2, (short) 0),
				new ItemStack(Material.SPIDER_EYE, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.CAVE_SPIDER, 1);

		hunting.put(EntityType.DOLPHIN, new ItemStack[] { new ItemStack(Material.COD, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.DOLPHIN, 1);

		hunting.put(EntityType.ENDERMAN, new ItemStack[] { new ItemStack(Material.ENDER_PEARL, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.ENDERMAN, 1);

		hunting.put(EntityType.GOAT, new ItemStack[] {});
		hunting_exp.put(EntityType.GOAT, 1);

		hunting.put(EntityType.IRON_GOLEM, new ItemStack[] { new ItemStack(Material.IRON_INGOT, 1 + 2, (short) 3),
				new ItemStack(Material.POPPY, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.IRON_GOLEM, 1);

		hunting.put(EntityType.LLAMA, new ItemStack[] { new ItemStack(Material.LEATHER, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.LLAMA, 1);

		hunting.put(EntityType.PANDA, new ItemStack[] { new ItemStack(Material.BAMBOO, 1 + 0, (short) 1) });
		hunting_exp.put(EntityType.PANDA, 1);

		hunting.put(EntityType.PIGLIN, new ItemStack[] { new ItemStack(Material.GOLDEN_SWORD, 1 + 1, (short) 0),
				new ItemStack(Material.CROSSBOW, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.PIGLIN, 1);

		hunting.put(EntityType.POLAR_BEAR, new ItemStack[] { new ItemStack(Material.COD, 1 + 2, (short) 0),
				new ItemStack(Material.SALMON, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.POLAR_BEAR, 1);

		hunting.put(EntityType.SPIDER, new ItemStack[] { new ItemStack(Material.STRING, 1 + 2, (short) 0),
				new ItemStack(Material.SPIDER_EYE, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.SPIDER, 1);

		hunting.put(EntityType.TRADER_LLAMA, new ItemStack[] { new ItemStack(Material.LEATHER, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.TRADER_LLAMA, 1);

		hunting.put(EntityType.WOLF, new ItemStack[] {});
		hunting_exp.put(EntityType.WOLF, 1);

		hunting.put(EntityType.ZOMBIFIED_PIGLIN,
				new ItemStack[] { new ItemStack(Material.ROTTEN_FLESH, 1 + 1, (short) 0),
						new ItemStack(Material.GOLD_NUGGET, 1 + 1, (short) 0),
						new ItemStack(Material.GOLD_INGOT, 1 + 1, (short) 0),
						new ItemStack(Material.GOLDEN_SWORD, 1 + 1, (short) 0),
						new ItemStack(Material.CROSSBOW, 1 + 1, (short) 0),
						new ItemStack(Material.GOLDEN_AXE, 1 + 1, (short) 0),
						new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.ZOMBIFIED_PIGLIN, 1);
		// 사냥 - 적대
		hunting.put(EntityType.BLAZE, new ItemStack[] { new ItemStack(Material.BLAZE_ROD, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.BLAZE, 1);

		hunting.put(EntityType.CREEPER, new ItemStack[] { new ItemStack(Material.GUNPOWDER, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.CREEPER, 1);

		hunting.put(EntityType.DROWNED,
				new ItemStack[] { new ItemStack(Material.ROTTEN_FLESH, 1 + 2, (short) 0),
						new ItemStack(Material.COPPER_INGOT, 1 + 1, (short) 0),
						new ItemStack(Material.TRIDENT, 1 + 1, (short) 0),
						new ItemStack(Material.FISHING_ROD, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.DROWNED, 1);

		hunting.put(EntityType.ELDER_GUARDIAN,
				new ItemStack[] { new ItemStack(Material.PRISMARINE_SHARD, 1 + 2, (short) 0),
						new ItemStack(Material.WET_SPONGE, 1 + 0, (short) 1),
						new ItemStack(Material.COD, 1 + 1, (short) 0),
						new ItemStack(Material.PRISMARINE_CRYSTALS, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.ELDER_GUARDIAN, 1);

		hunting.put(EntityType.ENDERMITE, new ItemStack[] {});
		hunting_exp.put(EntityType.ENDERMITE, 1);

		hunting.put(EntityType.EVOKER, new ItemStack[] { new ItemStack(Material.TOTEM_OF_UNDYING, 1 + 0, (short) 1),
				new ItemStack(Material.EMERALD, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.EVOKER, 1);

		hunting.put(EntityType.GHAST, new ItemStack[] { new ItemStack(Material.GHAST_TEAR, 1 + 1, (short) 0),
				new ItemStack(Material.GUNPOWDER, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.GHAST, 1);

		hunting.put(EntityType.GUARDIAN, new ItemStack[] { new ItemStack(Material.PRISMARINE_SHARD, 1 + 2, (short) 0),
				new ItemStack(Material.WET_SPONGE, 1 + 0, (short) 1), new ItemStack(Material.COD, 1 + 1, (short) 0),
				new ItemStack(Material.PRISMARINE_CRYSTALS, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.GUARDIAN, 1);

		hunting.put(EntityType.HOGLIN, new ItemStack[] { new ItemStack(Material.PORKCHOP, 1 + 2, (short) 2),
				new ItemStack(Material.LEATHER, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.HOGLIN, 1);

		hunting.put(EntityType.HUSK, new ItemStack[] { new ItemStack(Material.ROTTEN_FLESH, 1 + 2, (short) 0),
				new ItemStack(Material.IRON_INGOT, 1 + 1, (short) 0), new ItemStack(Material.CARROT, 1 + 1, (short) 0),
				new ItemStack(Material.POTATO, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.HUSK, 1);

		hunting.put(EntityType.MAGMA_CUBE, new ItemStack[] { new ItemStack(Material.MAGMA_CREAM, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.MAGMA_CUBE, 1);

		hunting.put(EntityType.PHANTOM, new ItemStack[] { new ItemStack(Material.PHANTOM_MEMBRANE, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.PHANTOM, 1);

		hunting.put(EntityType.PIGLIN_BRUTE, new ItemStack[] { new ItemStack(Material.GOLDEN_AXE, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.PIGLIN_BRUTE, 1);

		hunting.put(EntityType.PILLAGER, new ItemStack[] { new ItemStack(Material.CROSSBOW, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.PILLAGER, 1);

		hunting.put(EntityType.RAVAGER, new ItemStack[] { new ItemStack(Material.SADDLE, 1 + 0, (short) 1) });
		hunting_exp.put(EntityType.RAVAGER, 1);

		hunting.put(EntityType.SHULKER, new ItemStack[] { new ItemStack(Material.SHULKER_SHELL, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.SHULKER, 1);

		hunting.put(EntityType.SILVERFISH, new ItemStack[] {});
		hunting_exp.put(EntityType.SILVERFISH, 1);

		hunting.put(EntityType.PILLAGER,
				new ItemStack[] { new ItemStack(Material.BONE, 1 + 2, (short) 0),
						new ItemStack(Material.ARROW, 1 + 2, (short) 0),
						new ItemStack(Material.SKELETON_SKULL, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.PILLAGER, 1);

		hunting.put(EntityType.SLIME, new ItemStack[] { new ItemStack(Material.SLIME_BALL, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.SLIME, 1);

		ItemStack slow_arrow = new ItemStack(Material.TIPPED_ARROW, 0 + 1, (short) 0);

		hunting.put(EntityType.STRAY, new ItemStack[] { new ItemStack(Material.BONE, 1 + 2, (short) 0),
				new ItemStack(Material.ARROW, 1 + 2, (short) 0), slow_arrow });
		hunting_exp.put(EntityType.MAGMA_CUBE, 1);

		hunting.put(EntityType.VEX, new ItemStack[] {});
		hunting_exp.put(EntityType.VEX, 1);

		hunting.put(EntityType.VINDICATOR, new ItemStack[] { new ItemStack(Material.EMERALD, 1 + 1, (short) 0),
				new ItemStack(Material.IRON_AXE, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.VINDICATOR, 1);

		hunting.put(EntityType.WITHER, new ItemStack[] { new ItemStack(Material.GLASS_BOTTLE, 1 + 1, (short) 0),
				new ItemStack(Material.GLOWSTONE_DUST, 1 + 1, (short) 0),
				new ItemStack(Material.GUNPOWDER, 1 + 1, (short) 0), new ItemStack(Material.REDSTONE, 1 + 1, (short) 0),
				new ItemStack(Material.SPIDER_EYE, 1 + 1, (short) 0), new ItemStack(Material.SUGAR, 1 + 1, (short) 0),
				new ItemStack(Material.STICK, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.WITHER, 1);

		hunting.put(EntityType.WITHER_SKELETON,
				new ItemStack[] { new ItemStack(Material.BONE, 1 + 2, (short) 0),
						new ItemStack(Material.COAL, 1 + 1, (short) 0),
						new ItemStack(Material.WITHER_SKELETON_SKULL, 1 + 1, (short) 0),
						new ItemStack(Material.IRON_SWORD, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.WITHER_SKELETON, 1);

		hunting.put(EntityType.ZOGLIN, new ItemStack[] { new ItemStack(Material.ROTTEN_FLESH, 1 + 2, (short) 1) });
		hunting_exp.put(EntityType.ZOGLIN, 1);

		hunting.put(EntityType.ZOMBIE, new ItemStack[] { new ItemStack(Material.ROTTEN_FLESH, 1 + 2, (short) 0),
				new ItemStack(Material.IRON_INGOT, 1 + 1, (short) 0), new ItemStack(Material.CARROT, 1 + 1, (short) 0),
				new ItemStack(Material.POTATO, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.ZOMBIE, 1);

		hunting.put(EntityType.ZOMBIE_VILLAGER,
				new ItemStack[] { new ItemStack(Material.ROTTEN_FLESH, 1 + 2, (short) 0),
						new ItemStack(Material.IRON_INGOT, 1 + 1, (short) 0),
						new ItemStack(Material.CARROT, 1 + 1, (short) 0),
						new ItemStack(Material.POTATO, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.ZOMBIE_VILLAGER, 1);
		// 사냥 - 보스
		hunting.put(EntityType.ENDER_DRAGON, new ItemStack[] {});
		hunting_exp.put(EntityType.ENDER_DRAGON, 1);

		hunting.put(EntityType.WITHER, new ItemStack[] { new ItemStack(Material.NETHER_STAR, 1 + 0, (short) 0) });
		hunting_exp.put(EntityType.WITHER, 1);

		// 사냥 - 인공 스폰
		hunting.put(EntityType.GIANT, new ItemStack[] {});
		hunting_exp.put(EntityType.GIANT, 1);

		hunting.put(EntityType.ZOMBIE_HORSE,
				new ItemStack[] { new ItemStack(Material.ROTTEN_FLESH, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.ZOMBIE_HORSE, 1);

		hunting.put(EntityType.ILLUSIONER, new ItemStack[] { new ItemStack(Material.BOW, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.ILLUSIONER, 1);

		// 광질
		mining.put(Material.COAL_ORE, Material.COAL);
		mining_exp.put(Material.COAL_ORE, 1);
		mining.put(Material.DEEPSLATE_COAL_ORE, Material.COAL);
		mining_exp.put(Material.DEEPSLATE_COAL_ORE, 1);

		mining.put(Material.COPPER_ORE, Material.RAW_COPPER);
		mining_exp.put(Material.COPPER_ORE, 1);
		mining.put(Material.DEEPSLATE_COPPER_ORE, Material.RAW_COPPER);
		mining_exp.put(Material.DEEPSLATE_COPPER_ORE, 1);

		mining.put(Material.IRON_ORE, Material.RAW_IRON);
		mining_exp.put(Material.IRON_ORE, 3);
		mining.put(Material.DEEPSLATE_IRON_ORE, Material.RAW_IRON);
		mining_exp.put(Material.DEEPSLATE_IRON_ORE, 3);

		mining.put(Material.GOLD_ORE, Material.RAW_GOLD);
		mining_exp.put(Material.GOLD_ORE, 3);
		mining.put(Material.DEEPSLATE_GOLD_ORE, Material.RAW_GOLD);
		mining_exp.put(Material.DEEPSLATE_GOLD_ORE, 3);

		mining.put(Material.REDSTONE_ORE, Material.REDSTONE);
		mining_exp.put(Material.REDSTONE_ORE, 5);
		mining.put(Material.DEEPSLATE_REDSTONE_ORE, Material.REDSTONE);
		mining_exp.put(Material.DEEPSLATE_REDSTONE_ORE, 5);

		mining.put(Material.LAPIS_ORE, Material.LAPIS_LAZULI);
		mining_exp.put(Material.LAPIS_ORE, 5);
		mining.put(Material.DEEPSLATE_LAPIS_ORE, Material.LAPIS_LAZULI);
		mining_exp.put(Material.DEEPSLATE_LAPIS_ORE, 5);

		mining.put(Material.DIAMOND_ORE, Material.DIAMOND);
		mining_exp.put(Material.DIAMOND_ORE, 10);
		mining.put(Material.DEEPSLATE_DIAMOND_ORE, Material.DIAMOND);
		mining_exp.put(Material.DEEPSLATE_DIAMOND_ORE, 10);

		mining.put(Material.EMERALD_ORE, Material.EMERALD);
		mining_exp.put(Material.EMERALD_ORE, 100);
		mining.put(Material.DEEPSLATE_EMERALD_ORE, Material.EMERALD);
		mining_exp.put(Material.DEEPSLATE_EMERALD_ORE, 100);

		mining.put(Material.NETHER_GOLD_ORE, Material.RAW_GOLD);
		mining_exp.put(Material.NETHER_GOLD_ORE, 5);
		mining.put(Material.NETHER_QUARTZ_ORE, Material.QUARTZ);
		mining_exp.put(Material.NETHER_QUARTZ_ORE, 5);
		mining.put(Material.ANCIENT_DEBRIS, Material.NETHERITE_SCRAP);
		mining_exp.put(Material.ANCIENT_DEBRIS, 100);
		// 벌목
		chopping.put(Material.BAMBOO, Material.BAMBOO);
		chopping_exp.put(Material.BAMBOO, 1);
		
		chopping.put(Material.ACACIA_LOG, Material.ACACIA_LOG);
		chopping_exp.put(Material.ACACIA_LOG, 2);
		chopping.put(Material.ACACIA_LEAVES, Material.ACACIA_LEAVES);
		chopping_exp.put(Material.ACACIA_LEAVES, 1);
		
		chopping.put(Material.BIRCH_LOG, Material.BIRCH_LOG);
		chopping_exp.put(Material.BIRCH_LOG, 2);
		chopping.put(Material.BIRCH_LEAVES, Material.BIRCH_LEAVES);
		chopping_exp.put(Material.BIRCH_LEAVES, 1);
		
		chopping.put(Material.DARK_OAK_LOG, Material.DARK_OAK_LOG);
		chopping_exp.put(Material.DARK_OAK_LOG, 2);
		chopping.put(Material.DARK_OAK_LEAVES, Material.DARK_OAK_LEAVES);
		chopping_exp.put(Material.DARK_OAK_LEAVES, 1);
		
		chopping.put(Material.JUNGLE_LOG, Material.JUNGLE_LOG);
		chopping_exp.put(Material.JUNGLE_LOG, 2);
		chopping.put(Material.JUNGLE_LEAVES, Material.JUNGLE_LEAVES);
		chopping_exp.put(Material.JUNGLE_LEAVES, 1);
		
		chopping.put(Material.OAK_LOG, Material.OAK_LOG);
		chopping_exp.put(Material.OAK_LOG, 2);
		chopping.put(Material.OAK_LEAVES, Material.OAK_LEAVES);
		chopping_exp.put(Material.OAK_LEAVES, 1);
		
		chopping.put(Material.SPRUCE_LOG, Material.SPRUCE_LOG);
		chopping_exp.put(Material.SPRUCE_LOG, 2);
		chopping.put(Material.SPRUCE_LEAVES, Material.SPRUCE_LEAVES);
		chopping_exp.put(Material.SPRUCE_LEAVES, 1);
		
		// 고고학

		
		// 요리

		// 양조
	}
}
