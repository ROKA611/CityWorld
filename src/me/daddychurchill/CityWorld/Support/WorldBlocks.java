package me.daddychurchill.CityWorld.Support;

import org.bukkit.Material;
import org.bukkit.block.Block;
import me.daddychurchill.CityWorld.WorldGenerator;

public class WorldBlocks extends SupportChunk {

	private boolean doPhysics;
	
	public WorldBlocks(WorldGenerator generator) {
		super(generator);
		
		doPhysics = false;
	}

	public boolean getDoPhysics() {
		return doPhysics;
	}
	
	public void setDoPhysics(boolean dophysics) {
		doPhysics = dophysics;
	}

	public Block getActualBlock(int x, int y, int z) {
		return world.getBlockAt(x, y, z);
	}

	@Override
	protected void setBlock(int x, int y, int z, byte materialId) {
		world.getBlockAt(x, y, z).setTypeId(materialId);
	}

	@Override
	protected void setBlocks(int x1, int x2, int y, int z1, int z2, byte materialId) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				world.getBlockAt(x, y, z).setTypeId(materialId);
			}
		}
	}
	
	public void setBlock(int x, int y, int z, Material material) {
		world.getBlockAt(chunkX + x, y, chunkZ + z).setTypeId(material.getId(), doPhysics);
	}

	public void setBlock(int x, int y, int z, int type, byte data) {
		world.getBlockAt(chunkX + x, y, chunkZ + z).setTypeIdAndData(type, data, doPhysics);
	}
	
	public void setBlock(int x, int y, int z, Material material, boolean aDoPhysics) {
		world.getBlockAt(chunkX + x, y, chunkZ + z).setTypeId(material.getId(), aDoPhysics);
	}

	public void setBlock(int x, int y, int z, int type, byte data, boolean aDoPhysics) {
		world.getBlockAt(chunkX + x, y, chunkZ + z).setTypeIdAndData(type, data, aDoPhysics);
	}
	
	public void setBlocks(int x, int y1, int y2, int z, Material material) {
		for (int y = y1; y < y2; y++)
			world.getBlockAt(x, y, z).setType(material);
	}

	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					world.getBlockAt(x, y, z).setType(material);
				}
			}
		}
	}

	public void setBlocks(int x, int y1, int y2, int z, Material material, byte data) {
		for (int y = y1; y < y2; y++)
			world.getBlockAt(x, y, z).setTypeIdAndData(material.getId(), data, doPhysics);
	}

	public void setBlocks(int x, int y1, int y2, int z, Material material, byte data, boolean aDoPhysics) {
		for (int y = y1; y < y2; y++)
			world.getBlockAt(x, y, z).setTypeIdAndData(material.getId(), data, aDoPhysics);
	}

	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material, byte data, boolean aDoPhysics) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					world.getBlockAt(x, y, z).setTypeIdAndData(material.getId(), data, aDoPhysics);
				}
			}
		}
	}

	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material, byte data) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					world.getBlockAt(x, y, z).setTypeIdAndData(material.getId(), data, doPhysics);
				}
			}
		}
	}

	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, int type, byte data) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					world.getBlockAt(x, y, z).setTypeIdAndData(type, data, doPhysics);
				}
			}
		}
	}
	
	public void clearBlock(int x, int y, int z) {
		world.getBlockAt(x, y, z).setType(Material.AIR);
	}

	public void setBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				world.getBlockAt(x, y, z).setTypeId(material.getId(), doPhysics);
			}
		}
	}

	public void setBlocks(int x1, int x2, int y, int z1, int z2, Material material, boolean aDoPhysics) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				world.getBlockAt(x, y, z).setTypeId(material.getId(), aDoPhysics);
			}
		}
	}

	public void setBlocks(int x1, int x2, int y, int z1, int z2, Material material, byte data, boolean aDoPhysics) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				world.getBlockAt(x, y, z).setTypeIdAndData(material.getId(), data, aDoPhysics);
			}
		}
	}

	public void setBlocks(int x1, int x2, int y, int z1, int z2, Material material, byte data) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				world.getBlockAt(x, y, z).setTypeIdAndData(material.getId(), data, doPhysics);
			}
		}
	}

	public void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		setBlocks(x1, x2, y1, y2, z1, z1 + 1, material);
		setBlocks(x1, x2, y1, y2, z2 - 1, z2, material);
		setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material);
		setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material);
	}
	
	public void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, int type, byte data) {
		setBlocks(x1, x2, y1, y2, z1, z1 + 1, type, data);
		setBlocks(x1, x2, y1, y2, z2 - 1, z2, type, data);
		setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, type, data);
		setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, type, data);
	}
	
	public boolean setEmptyBlock(int x, int y, int z, Material material) {
		Block block = world.getBlockAt(x, y, z);
		if (block.isEmpty()) {
			block.setTypeId(material.getId(), doPhysics);
			return true;
		} else
			return false;
	}

	public boolean setEmptyBlock(int x, int y, int z, int type, byte data) {
		Block block = world.getBlockAt(x, y, z);
		if (block.isEmpty()) {
			block.setTypeIdAndData(type, data, doPhysics);
			return true;
		} else
			return false;
	}
	
	public boolean setEmptyBlock(int x, int y, int z, Material material, boolean aDoPhysics) {
		Block block = world.getBlockAt(x, y, z);
		if (block.isEmpty()) {
			block.setTypeId(material.getId(), aDoPhysics);
			return true;
		} else
			return false;
	}

	public boolean setEmptyBlock(int x, int y, int z, int type, byte data, boolean aDoPhysics) {
		Block block = world.getBlockAt(x, y, z);
		if (block.isEmpty()) {
			block.setTypeIdAndData(type, data, aDoPhysics);
			return true;
		} else
			return false;
	}
	
	public void setEmptyBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				Block block = world.getBlockAt(x, y, z);
				if (block.isEmpty())
					block.setType(material);
			}
		}
	}
	
	public void setEmptyBlocks(int x1, int x2, int y, int z1, int z2, int type, byte data, boolean aDoPhysics) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				Block block = world.getBlockAt(x, y, z);
				if (block.isEmpty())
					block.setTypeIdAndData(type, data, aDoPhysics);
			}
		}
	}
	
	public int findLastEmptyAbove(int x, int y, int z) {
		int y1 = y;
		while (y1 < height - 1 && world.getBlockAt(x, y1 + 1, z).isEmpty()) {
			y1++;
		}
		return y1;
	}
	
	public int findLastEmptyBelow(int x, int y, int z) {
		int y1 = y;
		while (y1 > 0 && world.getBlockAt(x, y1 - 1, z).isEmpty()) {
			y1--;
		}
		return y1;
	}
	
	public int setLayer(int blocky, Material material) {
		setBlocks(0, width, blocky, blocky + 1, 0, width, material);
		return blocky + 1;
	}

	public int setLayer(int blocky, int height, Material material) {
		setBlocks(0, width, blocky, blocky + height, 0, width, material);
		return blocky + height;
	}

	public int setLayer(int blocky, int height, int inset, Material material) {
		setBlocks(inset, width - inset, blocky, blocky + height, inset, width - inset, material);
		return blocky + height;
	}
	
	public boolean isEmpty(int x, int y, int z) {
		return world.getBlockAt(x, y, z).isEmpty();
	}
	
	public boolean isPlantable(int x, int y, int z) {
		return world.getBlockAt(x, y, z).getTypeId() == grassId;
	}
	
//	private void setDoor(int x, int y, int z, int doorId, Direction.Door direction) {
//		byte orentation = 0;
//		byte hinge = 0;
//		
//		// orientation
//		switch (direction) {
//		case NORTHBYNORTHEAST:
//		case NORTHBYNORTHWEST:
//			orentation = 1;
//			break;
//		case SOUTHBYSOUTHEAST:
//		case SOUTHBYSOUTHWEST:
//			orentation = 3;
//			break;
//		case WESTBYNORTHWEST:
//		case WESTBYSOUTHWEST:
//			orentation = 0;
//			break;
//		case EASTBYNORTHEAST:
//		case EASTBYSOUTHEAST:
//			orentation = 2;
//			break;
//		}
//		
//		// hinge?
//		switch (direction) {
//		case SOUTHBYSOUTHEAST:
//		case NORTHBYNORTHWEST:
//		case WESTBYSOUTHWEST:
//		case EASTBYNORTHEAST:
//			hinge = 8 + 0;
//			break;
//		case NORTHBYNORTHEAST:
//		case SOUTHBYSOUTHWEST:
//		case WESTBYNORTHWEST:
//		case EASTBYSOUTHEAST:
//			hinge = 8 + 1;
//			break;
//		}
//		
//		// set the door
//		world.getBlockAt(x, y + 1, z).setTypeIdAndData(doorId, hinge, false);
//		world.getBlockAt(x, y    , z).setTypeIdAndData(doorId, orentation, doPhysics);
//	}
//
//	public void setWoodenDoor(int x, int y, int z, Direction.Door direction) {
//		setDoor(x, y, z, Material.WOODEN_DOOR.getId(), direction);
//	}
//
//	public void setIronDoor(int x, int y, int z, Direction.Door direction) {
//		setDoor(x, y, z, Material.IRON_DOOR_BLOCK.getId(), direction);
//	}
//
//	public void setTrapDoor(int x, int y, int z, Direction.TrapDoor direction) {
//		setBlock(x, y, z, Material.TRAP_DOOR.getId(), direction.getData());
//	}
//
//	public void setStoneSlab(int x, int y, int z, Direction.StoneSlab direction) {
//		setBlock(x, y, z, Material.STEP.getId(), direction.getData());
//	}
//
//	//TODO change this to actual wood in 1.3
//	public void setWoodSlab(int x, int y, int z, Direction.WoodSlab direction) {
//		setBlock(x, y, z, Material.STEP.getId(), direction.getData());
//	}
//
//	public void setLadder(int x, int y1, int y2, int z, Direction.Ladder direction) {
//		int ladderId = Material.LADDER.getId();
//		byte data = direction.getData();
//		for (int y = y1; y < y2; y++)
//			setBlock(x, y, z, ladderId, data);
//	}
//
//	public void setStair(int x, int y, int z, Material material, Direction.Stair direction) {
//		setBlock(x, y, z, material.getId(), direction.getData());
//	}
//
//	public void setStair(int x, int y, int z, int type, Direction.Stair direction) {
//		setBlock(x, y, z, type, direction.getData());
//	}
//
//	public void setVine(int x, int y, int z, Direction.Vine direction) {
//		setBlock(x, y, z, Material.VINE.getId(), direction.getData());
//	}
//
//	public void setTorch(int x, int y, int z, Material material, Direction.Torch direction) {
//		setBlock(x, y, z, material.getId(), direction.getData(), true);
//	}
//	
//	public void setChest(int x, int y, int z, Direction.Chest direction, ItemStack... items) {
//		Block block = world.getBlockAt(x, y, z);
//		block.setTypeIdAndData(Material.CHEST.getId(), direction.getData(), true);
//		if (items.length > 0) {
////			Material material = block.getType();
////			BlockState state = block.getState();
////			CityWorld.log.info("BlockState = " + state);
////			CityWorld.log.info("BlockMaterial = " + material);
//			Chest chest = (Chest) block.getState();
//			Inventory inv = chest.getInventory();
//			inv.clear();
//			inv.addItem(items);
//		}
//	}
//
//	public void setSpawner(int x, int y, int z, EntityType aType) {
//		Block block = world.getBlockAt(x, y, z);
//		block.setType(Material.MOB_SPAWNER);
//		CreatureSpawner spawner = (CreatureSpawner) block.getState();
//		spawner.setSpawnedType(aType);
//		spawner.update(true);
//	}
//	
//	protected Material getRandomFlowerType(Random random) {
//		switch (random.nextInt(2)) {
//		case 1:
//			return Material.RED_ROSE;
//		default:
//			return Material.YELLOW_FLOWER;
//		}
//	}
//	
//	protected TreeType getRandomTreeType(Random random) {
//		switch (random.nextInt(3)) {
//		case 1:
//			return TreeType.BIRCH;
//		case 2:
//			return TreeType.REDWOOD;
//		default:
//			return TreeType.TREE;
//		}
//	}
//	
//	public void drawCrane(ContextData context, Random random, int x, int y, int z) {
//		
//		// vertical bit
//		setBlocks(x, y, y + 8, z, Material.IRON_FENCE);
//		setBlocks(x, y + 8, y + 10, z, Material.DOUBLE_STEP);
//		setBlocks(x - 1, y + 8, y + 10, z, Material.STEP);
//		setTorch(x, y + 10, z, context.torchMat, Torch.FLOOR);
//		
//		// horizontal bit
//		setBlock(x + 1, y + 8, z, Material.GLASS);
//		setBlocks(x + 2, x + 11, y + 8, y + 9, z, z + 1, Material.IRON_FENCE);
//		setBlocks(x + 1, x + 10, y + 9, y + 10, z, z + 1, Material.STEP);
//		setStair(x + 10, y + 9, z, Material.SMOOTH_STAIRS, Stair.WEST);
//		
//		// counter weight
//		setBlock(x - 2, y + 9, z, Material.STEP);
//		setStair(x - 3, y + 9, z, Material.SMOOTH_STAIRS, Stair.EAST);
//		setBlocks(x - 3, x - 1, y + 7, y + 9, z, z + 1, Material.WOOL, (byte) random.nextInt(16));
//	}
}
