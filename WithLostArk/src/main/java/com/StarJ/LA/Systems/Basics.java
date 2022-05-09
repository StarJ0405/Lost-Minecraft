package com.StarJ.LA.Systems;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.TreeType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.StarJ.LA.Items.DiggingItems;
import com.StarJ.LA.Systems.Fishes.Rarity;
import com.StarJ.LA.Systems.Comparables.ItemComparator;

public enum Basics {
	Farming("농사", Material.IRON_HOE) {
		@Override
		public int getNeedEXP(int level) {
			return level * level * 500;
		}

		@Override
		public BigDecimal getChance(int level) {
			return BigDecimal.ONE.subtract(new BigDecimal(level).multiply(new BigDecimal(0.015)));
		}

		@Override
		public List<String> getLore(int level) {
			List<String> lore = new ArrayList<String>();
			if (level >= 40) {
				lore.add(ChatColor.GREEN + "채집의 왕" + ChatColor.AQUA + " (쉬프트+쉬프트 / " + (450 - level * 3) + "초)");
				lore.add(ChatColor.WHITE + " - " + (level / 10 * 5 + 5) + "칸 내에 있는 농사물을 채집합니다.");
			}
			if (level >= 20) {
				lore.add(ChatColor.GREEN + "빠른 수거" + ChatColor.AQUA + " (쉬프트+좌클릭 / " + (150 - level * 2) + "초)");
				lore.add(ChatColor.WHITE + " - " + ((level / 5) * 2 + 5) + "칸 내에 농사물을 수거합니다.");
			}
			if (level >= 10) {
				lore.add(ChatColor.GREEN + "비료 뿌리기" + ChatColor.AQUA + " (쉬프트+우클릭 / " + (60 - level) + "초)");
				lore.add(ChatColor.WHITE + " - 비료를 뿌려 " + ((level / 10) * 2 + 3) + "칸 성장");
			}
			lore.add(ChatColor.GREEN + "범위 수확" + ChatColor.AQUA + " (우클릭 / 확정)");
			lore.add(ChatColor.WHITE + " - 괭이로 " + ((level / 10) * 2 + 3) + "칸을 수확합니다. "
					+ ((int) getChance(level).doubleValue() * 100) + "%로 내구도 소모");
			return lore;
		}
	},
	Fishing("낚시", Material.FISHING_ROD) {
		@Override
		public int getNeedEXP(int level) {
			return level * level * 10;
		}

		@Override
		public BigDecimal getChance(int level) {
			return new BigDecimal(level).multiply(new BigDecimal(0.005));
		}

		@Override
		public List<String> getLore(int level) {
			List<String> lore = new ArrayList<String>();
			if (level >= 40) {
				lore.add(ChatColor.GREEN + "낚시의 신" + ChatColor.AQUA + " (쉬프트+쉬프트 / " + (450 - level * 3) + "초)");
				lore.add(ChatColor.WHITE + " - 다음 " + (level / 10 + 1) + "회동안 RARE미만은 등장하지 않습니다.");
			}
			if (level >= 20) {
				lore.add(ChatColor.GREEN + "대어의 감" + ChatColor.AQUA + " (쉬프트+좌클릭 / " + (300 - level * 2) + "초)");
				lore.add(ChatColor.WHITE + " - 다음 " + (level / 10 + 1) + "회 높은 물고기가 " + (level) + "% 잘뜹니다.");
			}
			if (level >= 10) {
				lore.add(ChatColor.GREEN + "손맛" + ChatColor.AQUA + " (좌클릭 / " + (100 - level) + "초)");
				lore.add(ChatColor.WHITE + " - 다음 " + (level / 10 + 1) + "회 뛰어난 물고기를 낚아 " + (level * 4) + "% 비쌉니다.");
			}
			lore.add(ChatColor.GREEN + "어부지리" + ChatColor.AQUA + " (패시브 / 확률)");
			lore.add(ChatColor.WHITE + " - 물고기를 낚을 때 " + getChance(level).doubleValue() * 100 + "%로 " + (level / 10 + 1)
					+ "마리 획득합니다.");
			return lore;
		}
	},
	Hunting("사냥", Material.IRON_SWORD) {
		@Override
		public int getNeedEXP(int level) {
			return level * level * 10;
		}

		@Override
		public BigDecimal getChance(int level) {
			return new BigDecimal(level).multiply(new BigDecimal(0.015));
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
				lore.add(ChatColor.WHITE + " - " + (level / 2 + 5) + "칸 내에 동물들을 15초 동안 추적합니다.");
			}
			lore.add(ChatColor.GREEN + "훌륭한 사냥감" + ChatColor.AQUA + " (패시브 / 확정)");
			lore.add(ChatColor.WHITE + " - " + (getChance(level).doubleValue() * 100) + "%로 2-" + (level / 10 + 2)
					+ "개 추가 획득합니다.");

			return lore;
		}
	},
	Mining("광질", Material.IRON_PICKAXE) {
		@Override
		public int getNeedEXP(int level) {
			return level * level * 20;
		}

		@Override
		public BigDecimal getChance(int level) {
			return new BigDecimal(level).multiply(new BigDecimal(0.002));
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
			lore.add(ChatColor.WHITE + " - " + (getChance(level).doubleValue() * 100) + "%로 " + (level / 10 + 1)
					+ "개 추가 획득합니다.");
			return lore;
		}
	},
	Chopping("벌목", Material.IRON_AXE) {
		@Override
		public int getNeedEXP(int level) {
			return level * level * 100;
		}

		@Override
		public BigDecimal getChance(int level) {
			return new BigDecimal(level).multiply(new BigDecimal(0.01));
		}

		@Override
		public List<String> getLore(int level) {
			List<String> lore = new ArrayList<String>();
			if (level >= 40) {
				lore.add(ChatColor.GREEN + "포츈 타임" + ChatColor.AQUA + " (쉬프트+쉬프트 / " + (450 - level * 3) + "초)");
				lore.add(ChatColor.WHITE + " - " + (level / 2) + "초 동안 아이템을 추가 드랍합니다.");
			}
			if (level >= 20) {
				lore.add(ChatColor.GREEN + "나무 성장" + ChatColor.AQUA + " (쉬프트+우클릭 / " + (300 - level * 2) + "초)");
				lore.add(ChatColor.WHITE + " - " + (level / 2 + 5) + "범위내 묘목을 전부 성장시킵니다.");
			}
			if (level >= 10) {
				lore.add(ChatColor.GREEN + "진심 베기" + ChatColor.AQUA + " (우클릭 / " + (300 - level * 5) + "초)");
				lore.add(ChatColor.WHITE + " - 주변 " + (level / 2 + 5) + "범위 나무와 나뭇잎을 베어버립니다.");
			}
			lore.add(ChatColor.GREEN + "묘목 심기" + ChatColor.AQUA + " (패시브 / 확률)");
			lore.add(ChatColor.WHITE + " - " + (getChance(level).doubleValue() * 100) + "%로 해당 자리에 묘목을 심습니다.");
			return lore;
		}
	},
	Digging("고고학", Material.IRON_SHOVEL) {
		@Override
		public int getNeedEXP(int level) {
			return level * level * 50;
		}

		@Override
		public BigDecimal getChance(int level) {
			return new BigDecimal(level).multiply(new BigDecimal(0.001));
		}

		@Override
		public List<String> getLore(int level) {
			List<String> lore = new ArrayList<String>();
			if (level >= 40) {
				lore.add(ChatColor.GREEN + "완벽한 발굴" + ChatColor.AQUA + " (쉬프트+쉬프트 / " + (450 - level * 3) + "초)");
				lore.add(ChatColor.WHITE + " - " + (level / 10 + 1) + "회동안 보물을 발굴합니다.");
			}
			if (level >= 20) {
				lore.add(ChatColor.GREEN + "과감한 발굴" + ChatColor.AQUA + " (쉬프트+우클릭 / " + (300 - level * 2) + "초)");
				lore.add(ChatColor.WHITE + " - " + (level * 2) + "초동안 보물 확률이 " + getChance(level).doubleValue() * 100
						+ "% 증가합니다.");
			}
			if (level >= 10) {
				lore.add(ChatColor.GREEN + "노련한 탐색" + ChatColor.AQUA + " (우클릭 / " + (60 - level) * 5 + "초)");
				lore.add(ChatColor.WHITE + " - " + (level * 2) + "초동안 발굴범위가 " + (level / 10 * 2 + 3) + "칸 증가합니다.");
			}
			lore.add(ChatColor.GREEN + "보물 탐색" + ChatColor.AQUA + " (패시브 / 확률)");
			lore.add(ChatColor.WHITE + " - " + (getChance(level).doubleValue() * 100) + "%로 보물을 발견합니다.");
			return lore;
		}
	},
	Cooking("요리", Material.CAMPFIRE) {
		@Override
		public int getNeedEXP(int level) {
			return level * level * 5;
		}

		@Override
		public BigDecimal getChance(int level) {
			return new BigDecimal(level).multiply(new BigDecimal(0.02));
		}

		@Override
		public List<String> getLore(int level) {
			List<String> lore = new ArrayList<String>();
			if (level >= 40) {
				lore.add(ChatColor.GREEN + "달인" + ChatColor.AQUA + " (패시브 / 확률)");
				lore.add(ChatColor.WHITE + " - " + getChance(level).doubleValue() * 100 / 5 + "%로 요리 등급이 상승합니다.");
			}
			if (level >= 20) {
				lore.add(ChatColor.GREEN + "특별 요리" + ChatColor.AQUA + " (패시브 / 확률)");
				lore.add(ChatColor.WHITE + " - " + getChance(level).doubleValue() * 100 / 3 + "%로 추가효과가 붙습니다.");
			}
			if (level >= 10) {
				lore.add(ChatColor.GREEN + "고급 요리사" + ChatColor.AQUA + " (패시브 / 확률)");
				lore.add(ChatColor.WHITE + " - " + getChance(level).doubleValue() * 100 / 2 + "%로 요리 효과가 " + level * 2
						+ "% 증가합니다.");
			}
			lore.add(ChatColor.GREEN + "조리 도구" + ChatColor.AQUA + " (패시브 / 확률)");
			lore.add(ChatColor.WHITE + " - 요리시 " + (level / 10 + 4) + "개의 재료를 넣을 수 있습니다.");
			return lore;
		}
	},
	Potioning("양조", Material.BREWING_STAND) {
		@Override
		public int getNeedEXP(int level) {
			return level * level * 5;
		}

		@Override
		public BigDecimal getChance(int level) {
			return new BigDecimal(level).multiply(new BigDecimal(0.02));
		}

		@Override
		public List<String> getLore(int level) {
			List<String> lore = new ArrayList<String>();
			if (level >= 40) {
				lore.add(ChatColor.GREEN + "증식" + ChatColor.AQUA + " (패시브 / 확률)");
				lore.add(ChatColor.WHITE + " - " + getChance(level).doubleValue() * 100 / 5 + "%로 물약을 " + (level / 16)
						+ "개 추가 획득합니다..");
			}
			if (level >= 20) {
				lore.add(ChatColor.GREEN + "의도치않은 성공" + ChatColor.AQUA + " (패시브 / 확률)");
				lore.add(ChatColor.WHITE + " - " + getChance(level).doubleValue() * 100 / 3 + "%로 포션에 추가효과가 붙습니다.");
			}
			if (level >= 10) {
				lore.add(ChatColor.GREEN + "강력한 포션" + ChatColor.AQUA + " (패시브 / 확률)");
				lore.add(ChatColor.WHITE + " - " + getChance(level).doubleValue() * 100 / 2 + "%로 포션효과가 " + level * 2
						+ "% 강해집니다.");
			}
			lore.add(ChatColor.GREEN + "양조 도구" + ChatColor.AQUA + " (패시브 / 확률)");
			lore.add(ChatColor.WHITE + " - 양조시 " + (level / 10 + 4) + "개의 재료를 넣을 수 있습니다.");
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
	private static HashMap<Material, String> mining_name = new HashMap<Material, String>();
	private static HashMap<Material, Integer> mining_exp = new HashMap<Material, Integer>();
	private static HashMap<Material, ItemStack[]> chopping = new HashMap<Material, ItemStack[]>();
	private static HashMap<Material, Integer> chopping_exp = new HashMap<Material, Integer>();
	private static HashMap<Material, Material> chopping_sapling = new HashMap<Material, Material>();
	private static HashMap<Material, TreeType> chopping_treetype = new HashMap<Material, TreeType>();
	private static HashMap<Material, DiggingItems[]> digging = new HashMap<Material, DiggingItems[]>();
	private static HashMap<Material, Integer> digging_exp = new HashMap<Material, Integer>();
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

	public abstract BigDecimal getChance(int level);

	public abstract int getNeedEXP(int level);

	public abstract List<String> getLore(int level);

	public int getMaxLevel() {
		return 50;
	}

	public Material getIcon() {
		return icon;
	}

	public boolean isActive(int level) {
		return new Random().nextDouble() < getChance(level).doubleValue();
	}

	public void addEXP(Player player, int exp) {
		int level = getLevel(player);
		exp += getEXP(player);
		int need = getNeedEXP(level);
		if (exp >= need) {
			exp -= need;
			level++;
			player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 2.0f, 1.0f);
		}
		ConfigStore.setBasicsLevel(player, this, level);
		ConfigStore.setBasicsEXP(player, this, exp);
	}

	// 농사
	public static boolean isFarming(Material type) {
		return farming.containsKey(type);
	}

	public static boolean isFarmingCrops(ItemStack drop) {
		for (ItemStack[] items : farming.values())
			for (ItemStack item : items) {
				int comp = new ItemComparator().compare(item, drop);
				if (comp == 1 || comp == 2)
					return true;
			}
		return false;
	}

	public static ItemStack[] getFarming(Material type) {
		return farming.containsKey(type) ? farming.get(type) : new ItemStack[0];
	}

	public static int getFarmingExp(Material type) {
		return farming_exp.containsKey(type) ? farming_exp.get(type) : 0;
	}

	public static boolean hasFarmingData(Material type) {
		return farming_data.containsKey(type);
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

	public static String getMiningName(Material type) {
		return mining_name.containsKey(type) ? mining_name.get(type) : "";
	}

	public static int getMiningExp(Material type) {
		return mining_exp.containsKey(type) ? mining_exp.get(type) : 0;
	}

	// 벌목
	public static boolean isChopping(Material type) {
		return chopping.containsKey(type);
	}

	public static ItemStack[] getChopping(Material type) {
		return chopping.containsKey(type) ? chopping.get(type) : new ItemStack[0];
	}

	public static int getChoppingExp(Material type) {
		return chopping_exp.containsKey(type) ? chopping_exp.get(type) : 0;
	}

	public static boolean isChoppingSappling(Material type) {
		return chopping_sapling.containsKey(type);
	}

	public static Material getChoppingSappling(Material type) {
		return chopping_sapling.get(type);
	}

	public static boolean isChoppingTreetype(Material type) {
		return chopping_treetype.containsKey(type);
	}

	public static TreeType getChoppingTreetype(Material type) {
		return chopping_treetype.get(type);
	}

	// 고고학
	public static boolean isDigging(Material type) {
		return digging.containsKey(type);
	}

	public static DiggingItems[] getDigging(Material type) {
		return digging.containsKey(type) ? digging.get(type) : new DiggingItems[0];
	}

	public static int getDiggingExp(Material type) {
		return digging_exp.containsKey(type) ? digging_exp.get(type) : 0;
	}

	@SuppressWarnings("deprecation")
	public static void initial() {
		// 농사
		farming.put(Material.WHEAT, new ItemStack[] { new ItemStack(Material.WHEAT, 1 + 1, (byte) 0),
				new ItemStack(Material.WHEAT_SEEDS, 1 + 3, (short) 0) });
		farming_exp.put(Material.WHEAT, 3);
		farming_data.put(Material.WHEAT, (byte) 7);

		farming.put(Material.POTATOES, new ItemStack[] { new ItemStack(Material.POTATO, 1 + 4, (short) 1),
				new ItemStack(Material.POISONOUS_POTATO, 1 + 1, (short) 0) });
		farming_exp.put(Material.POTATOES, 3);
		farming_data.put(Material.POTATOES, (byte) 7);

		farming.put(Material.CARROTS, new ItemStack[] { new ItemStack(Material.CARROT, 1 + 3, (short) 2) });
		farming_exp.put(Material.CARROTS, 3);
		farming_data.put(Material.CARROTS, (byte) 7);

		farming.put(Material.BEETROOTS, new ItemStack[] { new ItemStack(Material.BEETROOT, 1 + 0, (short) 1),
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

		farming.put(Material.SWEET_BERRY_BUSH,
				new ItemStack[] { new ItemStack(Material.SWEET_BERRIES, 1 + 1, (short) 2) });
		farming_exp.put(Material.SWEET_BERRY_BUSH, 1);
		farming_data.put(Material.SWEET_BERRY_BUSH, (byte) 2);

		farming.put(Material.SUGAR_CANE, new ItemStack[] { new ItemStack(Material.SUGAR_CANE, 1 + 0, (short) 1) });
		farming_exp.put(Material.SUGAR_CANE, 1);

		farming.put(Material.KELP, new ItemStack[] { new ItemStack(Material.KELP, 1 + 0, (short) 1) });
		farming.put(Material.KELP_PLANT, new ItemStack[] { new ItemStack(Material.KELP, 1 + 0, (short) 1) });
		farming_exp.put(Material.KELP_PLANT, 1);

		// 낚시
		fishing_exp.put(Rarity.Trash, 1);
		fishing_exp.put(Rarity.Common, 2);
		fishing_exp.put(Rarity.Uncommon, 4);
		fishing_exp.put(Rarity.Rare, 8);
		fishing_exp.put(Rarity.Epic, 16);
		fishing_exp.put(Rarity.Treasure, 32);
		fishing_exp.put(Rarity.God, 64);

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
		hunting_exp.put(EntityType.CAVE_SPIDER, 5);

		hunting.put(EntityType.DOLPHIN, new ItemStack[] { new ItemStack(Material.COD, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.DOLPHIN, 1);

		hunting.put(EntityType.ENDERMAN, new ItemStack[] { new ItemStack(Material.ENDER_PEARL, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.ENDERMAN, 5);

		hunting.put(EntityType.GOAT, new ItemStack[] {});
		hunting_exp.put(EntityType.GOAT, 1);

		hunting.put(EntityType.IRON_GOLEM, new ItemStack[] { new ItemStack(Material.IRON_INGOT, 1 + 2, (short) 3),
				new ItemStack(Material.POPPY, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.IRON_GOLEM, 3);

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
		hunting_exp.put(EntityType.ZOMBIFIED_PIGLIN, 9);
		// 사냥 - 적대
		hunting.put(EntityType.BLAZE, new ItemStack[] { new ItemStack(Material.BLAZE_ROD, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.BLAZE, 5);

		hunting.put(EntityType.CREEPER, new ItemStack[] { new ItemStack(Material.GUNPOWDER, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.CREEPER, 1);

		hunting.put(EntityType.DROWNED,
				new ItemStack[] { new ItemStack(Material.ROTTEN_FLESH, 1 + 2, (short) 0),
						new ItemStack(Material.COPPER_INGOT, 1 + 1, (short) 0),
						new ItemStack(Material.TRIDENT, 1 + 1, (short) 0),
						new ItemStack(Material.FISHING_ROD, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.DROWNED, 3);

		hunting.put(EntityType.ELDER_GUARDIAN,
				new ItemStack[] { new ItemStack(Material.PRISMARINE_SHARD, 1 + 2, (short) 0),
						new ItemStack(Material.WET_SPONGE, 1 + 0, (short) 1),
						new ItemStack(Material.COD, 1 + 1, (short) 0),
						new ItemStack(Material.PRISMARINE_CRYSTALS, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.ELDER_GUARDIAN, 20);

		hunting.put(EntityType.ENDERMITE, new ItemStack[] {});
		hunting_exp.put(EntityType.ENDERMITE, 7);

		hunting.put(EntityType.EVOKER, new ItemStack[] { new ItemStack(Material.TOTEM_OF_UNDYING, 1 + 0, (short) 1),
				new ItemStack(Material.EMERALD, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.EVOKER, 20);

		hunting.put(EntityType.GHAST, new ItemStack[] { new ItemStack(Material.GHAST_TEAR, 1 + 1, (short) 0),
				new ItemStack(Material.GUNPOWDER, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.GHAST, 15);

		hunting.put(EntityType.GUARDIAN, new ItemStack[] { new ItemStack(Material.PRISMARINE_SHARD, 1 + 2, (short) 0),
				new ItemStack(Material.WET_SPONGE, 1 + 0, (short) 1), new ItemStack(Material.COD, 1 + 1, (short) 0),
				new ItemStack(Material.PRISMARINE_CRYSTALS, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.GUARDIAN, 10);

		hunting.put(EntityType.HOGLIN, new ItemStack[] { new ItemStack(Material.PORKCHOP, 1 + 2, (short) 2),
				new ItemStack(Material.LEATHER, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.HOGLIN, 3);

		hunting.put(EntityType.HUSK, new ItemStack[] { new ItemStack(Material.ROTTEN_FLESH, 1 + 2, (short) 0),
				new ItemStack(Material.IRON_INGOT, 1 + 1, (short) 0), new ItemStack(Material.CARROT, 1 + 1, (short) 0),
				new ItemStack(Material.POTATO, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.HUSK, 6);

		hunting.put(EntityType.MAGMA_CUBE, new ItemStack[] { new ItemStack(Material.MAGMA_CREAM, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.MAGMA_CUBE, 2);

		hunting.put(EntityType.PHANTOM, new ItemStack[] { new ItemStack(Material.PHANTOM_MEMBRANE, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.PHANTOM, 5);

		hunting.put(EntityType.PIGLIN_BRUTE, new ItemStack[] { new ItemStack(Material.GOLDEN_AXE, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.PIGLIN_BRUTE, 10);

		hunting.put(EntityType.PILLAGER, new ItemStack[] { new ItemStack(Material.CROSSBOW, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.PILLAGER, 5);

		hunting.put(EntityType.RAVAGER, new ItemStack[] { new ItemStack(Material.SADDLE, 1 + 0, (short) 1) });
		hunting_exp.put(EntityType.RAVAGER, 10);

		hunting.put(EntityType.SHULKER, new ItemStack[] { new ItemStack(Material.SHULKER_SHELL, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.SHULKER, 3);

		hunting.put(EntityType.SILVERFISH, new ItemStack[] {});
		hunting_exp.put(EntityType.SILVERFISH, 1);

		hunting.put(EntityType.SKELETON,
				new ItemStack[] { new ItemStack(Material.BONE, 1 + 2, (short) 0),
						new ItemStack(Material.ARROW, 1 + 2, (short) 0),
						new ItemStack(Material.SKELETON_SKULL, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.SKELETON, 3);

		hunting.put(EntityType.SLIME, new ItemStack[] { new ItemStack(Material.SLIME_BALL, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.SLIME, 1);

		ItemStack slow_arrow = new ItemStack(Material.TIPPED_ARROW, 0 + 1, (short) 0);

		hunting.put(EntityType.STRAY, new ItemStack[] { new ItemStack(Material.BONE, 1 + 2, (short) 0),
				new ItemStack(Material.ARROW, 1 + 2, (short) 0), slow_arrow });
		hunting_exp.put(EntityType.MAGMA_CUBE, 6);

		hunting.put(EntityType.VEX, new ItemStack[] {});
		hunting_exp.put(EntityType.VEX, 1);

		hunting.put(EntityType.VINDICATOR, new ItemStack[] { new ItemStack(Material.EMERALD, 1 + 1, (short) 0),
				new ItemStack(Material.IRON_AXE, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.VINDICATOR, 6);

		hunting.put(EntityType.WITCH, new ItemStack[] { new ItemStack(Material.GLASS_BOTTLE, 1 + 1, (short) 0),
				new ItemStack(Material.GLOWSTONE_DUST, 1 + 1, (short) 0),
				new ItemStack(Material.GUNPOWDER, 1 + 1, (short) 0), new ItemStack(Material.REDSTONE, 1 + 1, (short) 0),
				new ItemStack(Material.SPIDER_EYE, 1 + 1, (short) 0), new ItemStack(Material.SUGAR, 1 + 1, (short) 0),
				new ItemStack(Material.STICK, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.WITHER, 6);

		hunting.put(EntityType.WITHER_SKELETON,
				new ItemStack[] { new ItemStack(Material.BONE, 1 + 2, (short) 0),
						new ItemStack(Material.COAL, 1 + 1, (short) 0),
						new ItemStack(Material.WITHER_SKELETON_SKULL, 1 + 1, (short) 0),
						new ItemStack(Material.IRON_SWORD, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.WITHER_SKELETON, 6);

		hunting.put(EntityType.ZOGLIN, new ItemStack[] { new ItemStack(Material.ROTTEN_FLESH, 1 + 2, (short) 1) });
		hunting_exp.put(EntityType.ZOGLIN, 6);

		hunting.put(EntityType.ZOMBIE, new ItemStack[] { new ItemStack(Material.ROTTEN_FLESH, 1 + 2, (short) 0),
				new ItemStack(Material.IRON_INGOT, 1 + 1, (short) 0), new ItemStack(Material.CARROT, 1 + 1, (short) 0),
				new ItemStack(Material.POTATO, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.ZOMBIE, 3);

		hunting.put(EntityType.ZOMBIE_VILLAGER,
				new ItemStack[] { new ItemStack(Material.ROTTEN_FLESH, 1 + 2, (short) 0),
						new ItemStack(Material.IRON_INGOT, 1 + 1, (short) 0),
						new ItemStack(Material.CARROT, 1 + 1, (short) 0),
						new ItemStack(Material.POTATO, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.ZOMBIE_VILLAGER, 3);
		// 사냥 - 보스
		hunting.put(EntityType.ENDER_DRAGON, new ItemStack[] {});
		hunting_exp.put(EntityType.ENDER_DRAGON, 250);

		hunting.put(EntityType.WITHER, new ItemStack[] { new ItemStack(Material.NETHER_STAR, 1 + 0, (short) 0) });
		hunting_exp.put(EntityType.WITHER, 250);

		// 사냥 - 인공 스폰
		hunting.put(EntityType.GIANT, new ItemStack[] {});
		hunting_exp.put(EntityType.GIANT, 100);

		hunting.put(EntityType.ZOMBIE_HORSE,
				new ItemStack[] { new ItemStack(Material.ROTTEN_FLESH, 1 + 2, (short) 0) });
		hunting_exp.put(EntityType.ZOMBIE_HORSE, 1);

		hunting.put(EntityType.ILLUSIONER, new ItemStack[] { new ItemStack(Material.BOW, 1 + 1, (short) 0) });
		hunting_exp.put(EntityType.ILLUSIONER, 50);

		// 광질
		mining.put(Material.COAL_ORE, Material.COAL);
		mining_exp.put(Material.COAL_ORE, 1);
		mining_name.put(Material.COAL_ORE, "석탄 광석");
		mining.put(Material.DEEPSLATE_COAL_ORE, Material.COAL);
		mining_exp.put(Material.DEEPSLATE_COAL_ORE, 1);
		mining_name.put(Material.DEEPSLATE_COAL_ORE, "심층암 석탄 광석");

		mining.put(Material.COPPER_ORE, Material.RAW_COPPER);
		mining_exp.put(Material.COPPER_ORE, 1);
		mining_name.put(Material.COPPER_ORE, "구리 광석");
		mining.put(Material.DEEPSLATE_COPPER_ORE, Material.RAW_COPPER);
		mining_exp.put(Material.DEEPSLATE_COPPER_ORE, 1);
		mining_name.put(Material.DEEPSLATE_COPPER_ORE, "심층암 석탄 광석");

		mining.put(Material.IRON_ORE, Material.RAW_IRON);
		mining_exp.put(Material.IRON_ORE, 3);
		mining_name.put(Material.IRON_ORE, "철 광석");
		mining.put(Material.DEEPSLATE_IRON_ORE, Material.RAW_IRON);
		mining_exp.put(Material.DEEPSLATE_IRON_ORE, 3);
		mining_name.put(Material.DEEPSLATE_IRON_ORE, "심층암 철 광석");

		mining.put(Material.GOLD_ORE, Material.RAW_GOLD);
		mining_exp.put(Material.GOLD_ORE, 3);
		mining_name.put(Material.GOLD_ORE, "금 광석");
		mining.put(Material.DEEPSLATE_GOLD_ORE, Material.RAW_GOLD);
		mining_exp.put(Material.DEEPSLATE_GOLD_ORE, 3);
		mining_name.put(Material.DEEPSLATE_GOLD_ORE, "심층암 금 광석");

		mining.put(Material.REDSTONE_ORE, Material.REDSTONE);
		mining_exp.put(Material.REDSTONE_ORE, 5);
		mining_name.put(Material.REDSTONE_ORE, "레드스톤 광석");
		mining.put(Material.DEEPSLATE_REDSTONE_ORE, Material.REDSTONE);
		mining_exp.put(Material.DEEPSLATE_REDSTONE_ORE, 5);
		mining_name.put(Material.DEEPSLATE_REDSTONE_ORE, "심층암 레드스톤 광석");

		mining.put(Material.LAPIS_ORE, Material.LAPIS_LAZULI);
		mining_exp.put(Material.LAPIS_ORE, 5);
		mining_name.put(Material.LAPIS_ORE, "청금석 광석");
		mining.put(Material.DEEPSLATE_LAPIS_ORE, Material.LAPIS_LAZULI);
		mining_exp.put(Material.DEEPSLATE_LAPIS_ORE, 5);
		mining_name.put(Material.DEEPSLATE_LAPIS_ORE, "심층암 청금석 광석");

		mining.put(Material.DIAMOND_ORE, Material.DIAMOND);
		mining_exp.put(Material.DIAMOND_ORE, 30);
		mining_name.put(Material.DIAMOND_ORE, "다이아몬드 광석");
		mining.put(Material.DEEPSLATE_DIAMOND_ORE, Material.DIAMOND);
		mining_exp.put(Material.DEEPSLATE_DIAMOND_ORE, 30);
		mining_name.put(Material.DEEPSLATE_DIAMOND_ORE, "심층암 다이아몬드 광석");

		mining.put(Material.EMERALD_ORE, Material.EMERALD);
		mining_exp.put(Material.EMERALD_ORE, 100);
		mining_name.put(Material.EMERALD_ORE, "에메랄드 광석");
		mining.put(Material.DEEPSLATE_EMERALD_ORE, Material.EMERALD);
		mining_exp.put(Material.DEEPSLATE_EMERALD_ORE, 100);
		mining_name.put(Material.DEEPSLATE_EMERALD_ORE, "심층암 에멜라드 광석");

		mining.put(Material.NETHER_GOLD_ORE, Material.RAW_GOLD);
		mining_exp.put(Material.NETHER_GOLD_ORE, 5);
		mining_name.put(Material.NETHER_GOLD_ORE, "네더 금 광석");
		mining.put(Material.NETHER_QUARTZ_ORE, Material.QUARTZ);
		mining_name.put(Material.NETHER_QUARTZ_ORE, "네더 석영 광석");
		mining_exp.put(Material.NETHER_QUARTZ_ORE, 5);
		mining.put(Material.ANCIENT_DEBRIS, Material.NETHERITE_SCRAP);
		mining_exp.put(Material.ANCIENT_DEBRIS, 100);
		mining_name.put(Material.ANCIENT_DEBRIS, "고대 잔해");
		// 벌목
		chopping.put(Material.BAMBOO, new ItemStack[] { new ItemStack(Material.BAMBOO, 1 + 0, (byte) 1) });
		chopping_exp.put(Material.BAMBOO, 1);

		chopping.put(Material.ACACIA_LOG, new ItemStack[] { new ItemStack(Material.ACACIA_LOG, 1 + 0, (byte) 1) });
		chopping_exp.put(Material.ACACIA_LOG, 2);
		chopping.put(Material.ACACIA_LEAVES, new ItemStack[] { new ItemStack(Material.ACACIA_SAPLING, 1 + 1, (byte) 0),
				new ItemStack(Material.STICK, 1 + 2, (byte) 0), new ItemStack(Material.APPLE, 1 + 1, (byte) 0) });
		chopping_exp.put(Material.ACACIA_LEAVES, 1);
		chopping_treetype.put(Material.ACACIA_SAPLING, TreeType.ACACIA);
		chopping_sapling.put(Material.ACACIA_LOG, Material.ACACIA_SAPLING);

		chopping.put(Material.BIRCH_LOG, new ItemStack[] { new ItemStack(Material.BIRCH_LOG, 1 + 0, (byte) 1) });
		chopping_exp.put(Material.BIRCH_LOG, 2);
		chopping.put(Material.BIRCH_LEAVES, new ItemStack[] { new ItemStack(Material.BIRCH_SAPLING, 1 + 1, (byte) 0),
				new ItemStack(Material.STICK, 1 + 2, (byte) 0), new ItemStack(Material.APPLE, 1 + 1, (byte) 0) });
		chopping_exp.put(Material.BIRCH_LEAVES, 1);
		chopping_treetype.put(Material.BIRCH_SAPLING, TreeType.BIRCH);
		chopping_sapling.put(Material.BIRCH_LOG, Material.BIRCH_SAPLING);

		chopping.put(Material.DARK_OAK_LOG, new ItemStack[] { new ItemStack(Material.DARK_OAK_LOG, 1 + 0, (byte) 1) });
		chopping_exp.put(Material.DARK_OAK_LOG, 2);
		chopping.put(Material.DARK_OAK_LEAVES,
				new ItemStack[] { new ItemStack(Material.DARK_OAK_SAPLING, 1 + 1, (byte) 0),
						new ItemStack(Material.STICK, 1 + 2, (byte) 0),
						new ItemStack(Material.APPLE, 1 + 1, (byte) 0) });
		chopping_exp.put(Material.DARK_OAK_LEAVES, 1);
		chopping_treetype.put(Material.DARK_OAK_SAPLING, TreeType.DARK_OAK);
		chopping_sapling.put(Material.DARK_OAK_LOG, Material.DARK_OAK_SAPLING);

		chopping.put(Material.JUNGLE_LOG, new ItemStack[] { new ItemStack(Material.JUNGLE_LOG, 1 + 0, (byte) 1) });
		chopping_exp.put(Material.JUNGLE_LOG, 2);
		chopping.put(Material.JUNGLE_LEAVES, new ItemStack[] { new ItemStack(Material.JUNGLE_SAPLING, 1 + 1, (byte) 0),
				new ItemStack(Material.STICK, 1 + 2, (byte) 0), new ItemStack(Material.APPLE, 1 + 1, (byte) 0) });
		chopping_exp.put(Material.JUNGLE_LEAVES, 1);
		chopping_treetype.put(Material.JUNGLE_SAPLING, TreeType.JUNGLE);
		chopping_sapling.put(Material.JUNGLE_LOG, Material.JUNGLE_SAPLING);

		chopping.put(Material.OAK_LOG, new ItemStack[] { new ItemStack(Material.OAK_LOG, 1 + 0, (byte) 1) });
		chopping_exp.put(Material.OAK_LOG, 2);
		chopping.put(Material.OAK_LEAVES, new ItemStack[] { new ItemStack(Material.OAK_SAPLING, 1 + 1, (byte) 0),
				new ItemStack(Material.STICK, 1 + 2, (byte) 0), new ItemStack(Material.APPLE, 1 + 1, (byte) 0) });
		chopping_exp.put(Material.OAK_LEAVES, 1);
		chopping_treetype.put(Material.OAK_SAPLING, TreeType.TREE);
		chopping_sapling.put(Material.OAK_LOG, Material.OAK_SAPLING);

		chopping.put(Material.SPRUCE_LOG, new ItemStack[] { new ItemStack(Material.SPRUCE_LOG, 1 + 0, (byte) 1) });
		chopping_exp.put(Material.SPRUCE_LOG, 2);
		chopping.put(Material.SPRUCE_LEAVES, new ItemStack[] { new ItemStack(Material.SPRUCE_SAPLING, 1 + 1, (byte) 0),
				new ItemStack(Material.STICK, 1 + 2, (byte) 0), new ItemStack(Material.APPLE, 1 + 1, (byte) 0) });
		chopping_exp.put(Material.SPRUCE_LEAVES, 1);
		chopping_treetype.put(Material.SPRUCE_SAPLING, TreeType.REDWOOD);
		chopping_sapling.put(Material.SPRUCE_LOG, Material.SPRUCE_SAPLING);

		chopping.put(Material.MUSHROOM_STEM, new ItemStack[] { new ItemStack(Material.BROWN_MUSHROOM, 1 + 1, (byte) 0),
				new ItemStack(Material.RED_MUSHROOM, 1 + 1, (byte) 0) });
		chopping_exp.put(Material.MUSHROOM_STEM, 2);

		chopping.put(Material.BROWN_MUSHROOM_BLOCK,
				new ItemStack[] { new ItemStack(Material.BROWN_MUSHROOM, 1 + 1, (byte) 0) });
		chopping_exp.put(Material.BROWN_MUSHROOM_BLOCK, 2);
		chopping_treetype.put(Material.BROWN_MUSHROOM, TreeType.BROWN_MUSHROOM);

		chopping.put(Material.RED_MUSHROOM_BLOCK,
				new ItemStack[] { new ItemStack(Material.RED_MUSHROOM, 1 + 1, (byte) 0) });
		chopping_exp.put(Material.RED_MUSHROOM_BLOCK, 2);
		chopping_treetype.put(Material.RED_MUSHROOM, TreeType.RED_MUSHROOM);

		// 고고학
		digging.put(Material.DIRT, new DiggingItems[] {});
		digging_exp.put(Material.DIRT, 1);
		digging.put(Material.DIRT_PATH, new DiggingItems[] {});
		digging_exp.put(Material.DIRT_PATH, 1);
		digging.put(Material.COARSE_DIRT, new DiggingItems[] {});
		digging_exp.put(Material.COARSE_DIRT, 1);
		digging.put(Material.ROOTED_DIRT, new DiggingItems[] {});
		digging_exp.put(Material.ROOTED_DIRT, 1);

		digging.put(Material.CLAY, new DiggingItems[] {});
		digging_exp.put(Material.CLAY, 2);

		digging.put(Material.MYCELIUM, new DiggingItems[] {});
		digging_exp.put(Material.MYCELIUM, 3);

		digging.put(Material.GRASS, new DiggingItems[] {});
		digging_exp.put(Material.GRASS, 1);

		digging.put(Material.SAND, new DiggingItems[] {});
		digging_exp.put(Material.SAND, 1);

		digging.put(Material.RED_SAND, new DiggingItems[] {});
		digging_exp.put(Material.RED_SAND, 4);

		digging.put(Material.SOUL_SAND, new DiggingItems[] {});
		digging_exp.put(Material.SOUL_SAND, 3);
		digging.put(Material.SOUL_SOIL, new DiggingItems[] {});
		digging_exp.put(Material.SOUL_SOIL, 3);

		digging.put(Material.GRAVEL, new DiggingItems[] {});
		digging_exp.put(Material.GRAVEL, 2);

		digging.put(Material.BLACK_CONCRETE_POWDER, new DiggingItems[] {});
		digging_exp.put(Material.BLACK_CONCRETE_POWDER, 2);
		digging.put(Material.BLUE_CONCRETE_POWDER, new DiggingItems[] {});
		digging_exp.put(Material.BLUE_CONCRETE_POWDER, 2);
		digging.put(Material.BROWN_CONCRETE_POWDER, new DiggingItems[] {});
		digging_exp.put(Material.BROWN_CONCRETE_POWDER, 2);
		digging.put(Material.CYAN_CONCRETE_POWDER, new DiggingItems[] {});
		digging_exp.put(Material.CYAN_CONCRETE_POWDER, 2);
		digging.put(Material.GRAY_CONCRETE_POWDER, new DiggingItems[] {});
		digging_exp.put(Material.GRAY_CONCRETE_POWDER, 2);
		digging.put(Material.GREEN_CONCRETE_POWDER, new DiggingItems[] {});
		digging_exp.put(Material.GREEN_CONCRETE_POWDER, 2);
		digging.put(Material.LIGHT_BLUE_CONCRETE_POWDER, new DiggingItems[] {});
		digging_exp.put(Material.LIGHT_BLUE_CONCRETE_POWDER, 2);
		digging.put(Material.LIGHT_GRAY_CONCRETE_POWDER, new DiggingItems[] {});
		digging_exp.put(Material.LIGHT_GRAY_CONCRETE_POWDER, 2);
		digging.put(Material.LIME_CONCRETE_POWDER, new DiggingItems[] {});
		digging_exp.put(Material.LIME_CONCRETE_POWDER, 2);
		digging.put(Material.MAGENTA_CONCRETE_POWDER, new DiggingItems[] {});
		digging_exp.put(Material.MAGENTA_CONCRETE_POWDER, 2);
		digging.put(Material.ORANGE_CONCRETE_POWDER, new DiggingItems[] {});
		digging_exp.put(Material.ORANGE_CONCRETE_POWDER, 2);
		digging.put(Material.PINK_CONCRETE_POWDER, new DiggingItems[] {});
		digging_exp.put(Material.PINK_CONCRETE_POWDER, 2);
		digging.put(Material.PURPLE_CONCRETE_POWDER, new DiggingItems[] {});
		digging_exp.put(Material.PURPLE_CONCRETE_POWDER, 2);
		digging.put(Material.RED_CONCRETE_POWDER, new DiggingItems[] {});
		digging_exp.put(Material.RED_CONCRETE_POWDER, 2);
		digging.put(Material.WHITE_CONCRETE_POWDER, new DiggingItems[] {});
		digging_exp.put(Material.WHITE_CONCRETE_POWDER, 2);
		digging.put(Material.YELLOW_CONCRETE_POWDER, new DiggingItems[] {});
		digging_exp.put(Material.YELLOW_CONCRETE_POWDER, 2);
	}
}
