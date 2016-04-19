package me.daddychurchill.CityWorld.Plats.Urban;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.Populators.FactoryWithStuff;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.Surroundings;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.BadMagic.StairWell;

public class FactoryBuildingLot extends IndustrialBuildingLot {
	
	private static RoomProvider contentsStuff = new FactoryWithStuff();
	
	private final static double oddsOfSimilarContent = Odds.oddsUnlikely;

	private enum ContentStyle {TANK, STUFF, PIT, SMOKESTACK, OFFICE}; 
	private ContentStyle contentStyle;
	
	public FactoryBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		firstFloorHeight = DataContext.FloorHeight * (chunkOdds.getRandomInt(3) + 3);
		insetWallNS = 1;
		insetWallWE = 1;
		insetCeilingNS = 1;
		insetCeilingWE = 1;
		
		contentStyle = pickContentStyle(chunkOdds);
	}
	
	public ContentStyle pickContentStyle(Odds odds) {
		ContentStyle[] values = ContentStyle.values();
		return values[odds.getRandomInt(values.length)];
	}
	
	@Override
	public int getTopY(CityWorldGenerator generator) {
		return Math.max(super.getTopY(generator), generator.structureLevel + DataContext.FloorHeight * 10);
	}
	
	@Override
	protected boolean isShaftableLevel(CityWorldGenerator generator, int blockY) {
		return blockY < generator.structureLevel - 10;
	}
	
	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);
		
		// other bits
		if (result && relative instanceof FactoryBuildingLot) {
			FactoryBuildingLot relativebuilding = (FactoryBuildingLot) relative;

			// any other bits
			firstFloorHeight = relativebuilding.firstFloorHeight;
			
			if (chunkOdds.playOdds(oddsOfSimilarContent))
				contentStyle = relativebuilding.contentStyle;
		}
		
		return result;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FactoryBuildingLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected InteriorStyle pickInteriorStyle() {
		return InteriorStyle.EMPTY;
	}
	
	@Override
	protected void drawInteriorParts(CityWorldGenerator generator, RealBlocks chunk, DataContext context,
			RoomProvider rooms, int floor, int floorAt, int floorHeight, int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, StairWell stairLocation, Material materialStair,
			Material materialStairWall, Material materialPlatform, boolean drawStairWall, boolean drawStairs,
			boolean topFloor, boolean singleFloor, Surroundings heights) {
		
		int groundY = generator.structureLevel + 2;
		int skywalkHeight = firstFloorHeight / 2;
		int skywalkAt = groundY + skywalkHeight;
		
		Material airMat = generator.shapeProvider.findAtmosphereMaterialAt(generator, groundY);
		Material wallMat = generator.settings.materials.itemsSelectMaterial_FactoryInsides.getRandomMaterial(chunkOdds, Material.SMOOTH_BRICK);
		Material officeMat = generator.settings.materials.itemsSelectMaterial_FactoryInsides.getRandomMaterial(chunkOdds, Material.SMOOTH_BRICK);
		Material supportMat = generator.settings.materials.itemsSelectMaterial_FactoryInsides.getRandomMaterial(chunkOdds, Material.CLAY);
		Material smokestackMat = generator.settings.materials.itemsSelectMaterial_FactoryInsides.getRandomMaterial(chunkOdds, Material.CLAY);
		Material fluidMat = generator.settings.materials.itemsSelectMaterial_FactoryTanks.getRandomMaterial(chunkOdds, Material.STATIONARY_WATER);

		switch (contentStyle) {
		case OFFICE:
			
			// bottom floor
			chunk.setWalls(3, 13, groundY, groundY + 1, 3, 13, officeMat);
			chunk.setWalls(3, 13, groundY + 1, groundY + 2, 3, 13, Material.THIN_GLASS);
			chunk.setWalls(3, 13, groundY + 2, skywalkAt, 3, 13, officeMat);
			chunk.setBlocks(3, 13, skywalkAt, 3, 13, officeMat);
			generateOpenings(chunk, groundY);
			
			// room for middle floor?
			if (groundY + aboveFloorHeight <= skywalkAt - aboveFloorHeight) {
				int secondY = groundY + aboveFloorHeight;
				chunk.setBlocks(3, 13, secondY, 3, 13, officeMat);
				chunk.setWalls(3, 13, secondY + 2, secondY + 3, 3, 13, Material.THIN_GLASS);
			}

			// top floor at skywalk level
			chunk.setWalls(3, 13, skywalkAt + 1, skywalkAt + 2, 3, 13, officeMat);
			chunk.setWalls(3, 13, skywalkAt + 2, skywalkAt + 3, 3, 13, Material.THIN_GLASS);
			chunk.setWalls(3, 13, skywalkAt + 3, skywalkAt + 4, 3, 13, officeMat);
			chunk.setBlocks(3, 13, skywalkAt + 4, 3, 13, officeMat);
			generateOpenings(chunk, skywalkAt + 1);
			
			chunk.setBlocks(5, groundY, skywalkAt + 2, 5, officeMat);
			chunk.setLadder(5, groundY, skywalkAt + 2, 6, BlockFace.NORTH);

			generateSkyWalkBits(generator, chunk, heights, skywalkAt);
			break;
		case PIT:
			int topOfPit = groundY + 1;
			int bottomOfPit = topOfPit - 6;
			int pitLevel = topOfPit - chunkOdds.getRandomInt(3) - 1;
			
			chunk.setCircle(8, 8, 4, bottomOfPit - 1, wallMat, true);
			chunk.setCircle(8, 8, 4, bottomOfPit, topOfPit, airMat, true);
			chunk.setCircle(8, 8, 4, bottomOfPit, pitLevel, fluidMat, true);
			chunk.setCircle(8, 8, 4, bottomOfPit, topOfPit, wallMat); // put the wall up quick!

			generateSkyWalkCross(generator, chunk, heights, skywalkAt);
			break;
		case TANK:
			int topOfTank = skywalkAt + 2;
			int bottomOfTank = groundY + 4;
			int tankLevel = topOfTank - chunkOdds.getRandomInt(3) - 1;
			
			chunk.setCircle(8, 8, 4, bottomOfTank - 1, wallMat, true);
			chunk.setCircle(8, 8, 4, bottomOfTank, tankLevel, fluidMat, true);
			chunk.setCircle(8, 8, 4, bottomOfTank, topOfTank, wallMat); // put the wall up quick!

			chunk.setBlocks(4, 6, groundY, bottomOfTank + 1, 4, 6, supportMat);
			chunk.setBlocks(10, 12, groundY, bottomOfTank + 1, 4, 6, supportMat);
			chunk.setBlocks(4, 6, groundY, bottomOfTank + 1, 10, 12, supportMat);
			chunk.setBlocks(10, 12, groundY, bottomOfTank + 1, 10, 12, supportMat);
			
			generateSkyWalkBits(generator, chunk, heights, skywalkAt);
			break;
		case STUFF:
			generateStuff(generator, chunk, 2, groundY, 2, 3, 3);
			if (heights.toNorth())
				generateStuff(generator, chunk, 6, groundY, 2, 4, 3);
			generateStuff(generator, chunk, 11, groundY, 2, 3, 3);

			if (heights.toWest())
				generateStuff(generator, chunk, 2, groundY, 6, 3, 4);
			generateStuff(generator, chunk, 6, groundY, 6, 4, 4);
			if (heights.toEast())
				generateStuff(generator, chunk, 11, groundY, 6, 3, 4);
			
			generateStuff(generator, chunk, 2, groundY, 11, 3, 3);
			if (heights.toSouth())
				generateStuff(generator, chunk, 6, groundY, 11, 4, 3);
			generateStuff(generator, chunk, 11, groundY, 11, 3, 3);

//			chunk.setWool(3, 5, groundY, skywalkAt + chunkOdds.getRandomInt(5), 3, 5, chunkOdds.getRandomColor());
//			chunk.setWool(11, 13, groundY, skywalkAt + chunkOdds.getRandomInt(5), 3, 5, chunkOdds.getRandomColor());
//			chunk.setWool(3, 5, groundY, skywalkAt + chunkOdds.getRandomInt(5), 11, 13, chunkOdds.getRandomColor());
//			chunk.setWool(11, 13, groundY, skywalkAt + chunkOdds.getRandomInt(5), 11, 13, chunkOdds.getRandomColor());
//			
//			if (!neighborFloors.toNorth())
//				chunk.setWool(7, 9, groundY, skywalkAt + chunkOdds.getRandomInt(5), 3, 5, chunkOdds.getRandomColor());
//			if (!neighborFloors.toSouth())
//				chunk.setWool(7, 9, groundY, skywalkAt + chunkOdds.getRandomInt(5), 11, 13, chunkOdds.getRandomColor());
//			if (!neighborFloors.toWest())
//				chunk.setWool(3, 5, groundY, skywalkAt + chunkOdds.getRandomInt(5), 7, 9, chunkOdds.getRandomColor());
//			if (!neighborFloors.toEast())
//				chunk.setWool(11, 13, groundY, skywalkAt + chunkOdds.getRandomInt(5), 7, 9, chunkOdds.getRandomColor());
//			
			generateSkyWalkCross(generator, chunk, heights, skywalkAt);
			break;
		case SMOKESTACK:
			chunk.setWalls(3, 13, groundY, skywalkAt - 1, 3, 13, officeMat);
			chunk.setWalls(4, 12, skywalkAt - 1, skywalkAt, 4, 12, officeMat);
			chunk.setBlocks(3, 13, skywalkAt, 3, 13, officeMat);
			generateOpenings(chunk, groundY);

			int smokestackY1 = skywalkAt + firstFloorHeight;
			int smokestackY2 = smokestackY1 + chunkOdds.calcRandomRange(10, firstFloorHeight);
			int smokestackY3 = smokestackY2 + chunkOdds.calcRandomRange(10, firstFloorHeight);
			
			chunk.setBlocks(6, 10, groundY - 3, 6, 10, smokestackMat);
			chunk.clearBlocks(6, 10, groundY - 2, smokestackY1, 6, 10);
			chunk.setWalls(5, 11, groundY - 2, groundY, 5, 11, smokestackMat);
			chunk.setBlocks(6, 10, groundY - 2, 6, 10, Material.NETHERRACK);
			chunk.setWalls(5, 11, groundY, groundY + 6, 5, 11, smokestackMat);

			chunk.setThinGlass(8, groundY + 1, 5, DyeColor.RED);
			chunk.setThinGlass(7, groundY + 1, 10, DyeColor.RED);
			chunk.setThinGlass(5, groundY + 1, 8, DyeColor.RED);
			chunk.setThinGlass(10, groundY + 1, 7, DyeColor.RED);
			
			
			if (generator.settings.includeDecayedBuildings) {

				//TODO: nick up the smokestack and make smoke come out... maybe

				if (chunkOdds.playOdds(Odds.oddsLikely)) {
					chunk.setCircle(8, 8, 2, groundY + 6, smokestackY1, smokestackMat);
					chunk.pepperBlocks(5, 11, groundY, smokestackY1, 5, 11, chunkOdds, Odds.oddsPrettyUnlikely, Material.AIR);
					if (chunkOdds.playOdds(Odds.oddsSomewhatLikely)) {
						chunk.setWalls(6, 10, smokestackY1, smokestackY2, 6, 10, smokestackMat);
						chunk.pepperBlocks(6, 10, smokestackY1, smokestackY2, 6, 10, chunkOdds, Odds.oddsPrettyUnlikely, Material.AIR);
						if (chunkOdds.playOdds(Odds.oddsSomewhatLikely)) {
							chunk.setCircle(8, 8, 1, smokestackY2, smokestackY3, smokestackMat);
							chunk.pepperBlocks(7, 9, smokestackY2, smokestackY3, 7, 9, chunkOdds, Odds.oddsPrettyUnlikely, Material.AIR);
						}
					}
				}
				
				//TODO: half the time it doesn't have smoke 
//				if (chunkOdds.flipCoin()) {
//					chunk.setBlocks(6, 10, groundY - 1, 6, 10, Material.FIRE);
//					chunk.pepperBlocks(7, 9, smokestackY3 - 2, smokestackY3 + 6, 7, 9, chunkOdds, Material.WEB);
//				}
				
			} else {
				chunk.setBlocks(6, 10, groundY - 1, 6, 10, Material.FIRE);

				chunk.setCircle(8, 8, 2, groundY + 6, smokestackY1, smokestackMat);
				chunk.setWalls(6, 10, smokestackY1, smokestackY2, 6, 10, smokestackMat);
				chunk.setCircle(8, 8, 1, smokestackY2, smokestackY3, smokestackMat);

				chunk.pepperBlocks(7, 9, smokestackY3 - 2, smokestackY3 + 6, 7, 9, chunkOdds, Material.WEB);
			}
			
			generateSkyWalkBits(generator, chunk, heights, skywalkAt);
			break;
		}
	}
	
	protected void generateStuff(CityWorldGenerator generator, RealBlocks chunk, int x, int y, int z, int width, int depth) {
		contentsStuff.drawFixtures(generator, chunk, chunkOdds, 1, x, y, z, width, DataContext.FloorHeight, depth, Facing.NORTH, Material.STONE, Material.GLASS);
	}
	
	protected void generateOpenings(RealBlocks chunk, int y) {
		chunk.clearBlocks(7 + chunkOdds.getRandomInt(2), y, y + 2, 3);
		chunk.clearBlocks(7 + chunkOdds.getRandomInt(2), y, y + 2, 12);
		chunk.clearBlocks(3, y, y + 2, 7 + chunkOdds.getRandomInt(2));
		chunk.clearBlocks(12, y, y + 2, 7 + chunkOdds.getRandomInt(2));
	}
	
	protected void generateSkyWalkBits(CityWorldGenerator generator, RealBlocks chunk, Surroundings neighbors, int skywalkAt) {
		boolean doNorthward = neighbors.toNorth();
		boolean doSouthward = neighbors.toSouth();
		boolean doWestward = neighbors.toWest();
		boolean doEastward = neighbors.toEast();
		
		if (doNorthward) {
			generateSkyWalkBitsNS(chunk, 6, 0, skywalkAt);
		}
		if (doSouthward) {
			generateSkyWalkBitsNS(chunk, 6, 13, skywalkAt);
		}
		if (doWestward) {
			generateSkyWalkBitsWE(chunk, 0, 6, skywalkAt);
		}
		if (doEastward) {
			generateSkyWalkBitsWE(chunk, 13, 6, skywalkAt);
		}
		
	}
			
	private void generateSkyWalkBitsNS(RealBlocks chunk, int x, int z, int skywalkAt) {
		chunk.setBlocks(x, x + 4, skywalkAt, z, z + 3, ceilingMaterial);
		chunk.setBlocks(x, x + 1, skywalkAt + 1, z, z + 3, Material.IRON_FENCE);
		chunk.setBlocks(x + 3, x + 4, skywalkAt + 1, z, z + 3, Material.IRON_FENCE);
	}

	private void generateSkyWalkBitsWE(RealBlocks chunk, int x, int z, int skywalkAt) {
		chunk.setBlocks(x, x + 3, skywalkAt, z, z + 4, ceilingMaterial);
		chunk.setBlocks(x, x + 3, skywalkAt + 1, z, z + 1, Material.IRON_FENCE);
		chunk.setBlocks(x, x + 3, skywalkAt + 1, z + 3, z + 4, Material.IRON_FENCE);
	}

	protected void generateSkyWalkCross(CityWorldGenerator generator, RealBlocks chunk, Surroundings neighbors, 
			int skywalkAt) {
		boolean doNorthward = neighbors.toNorth();
		boolean doSouthward = neighbors.toSouth();
		boolean doWestward = neighbors.toWest();
		boolean doEastward = neighbors.toEast();
		
		if (doNorthward)
			generateSkyWalkNS(chunk, 6, 0, skywalkAt);
		if (doSouthward)
			generateSkyWalkNS(chunk, 6, 10, skywalkAt);
		if (doWestward)
			generateSkyWalkWE(chunk, 0, 6, skywalkAt);
		if (doEastward)
			generateSkyWalkWE(chunk, 10, 6, skywalkAt);
		
		chunk.setBlocks(6, 10, skywalkAt, 6, 10, ceilingMaterial);
		
		if (!doNorthward)
			chunk.setBlocks(7, 9, skywalkAt + 1, 6, 7, Material.IRON_FENCE);
		if (!doSouthward)
			chunk.setBlocks(7, 9, skywalkAt + 1, 9, 10, Material.IRON_FENCE);
		if (!doWestward)
			chunk.setBlocks(6, 7, skywalkAt + 1, 7, 9, Material.IRON_FENCE);
		if (!doEastward)
			chunk.setBlocks(9, 10, skywalkAt + 1, 7, 9, Material.IRON_FENCE);
		
		chunk.setBlocksUpward(6, skywalkAt + 1, 6, Material.IRON_FENCE);
		chunk.setBlocksUpward(6, skywalkAt + 1, 9, Material.IRON_FENCE);
		chunk.setBlocksUpward(9, skywalkAt + 1, 6, Material.IRON_FENCE);
		chunk.setBlocksUpward(9, skywalkAt + 1, 9, Material.IRON_FENCE);
	}
	
	private void generateSkyWalkNS(RealBlocks chunk, int x, int z, int skywalkAt) {
		chunk.setBlocks(x, x + 4, skywalkAt, z, z + 6, ceilingMaterial);
		chunk.setBlocks(x, x + 1, skywalkAt + 1, z, z + 6, Material.IRON_FENCE);
		chunk.setBlocks(x + 3, x + 4, skywalkAt + 1, z, z + 6, Material.IRON_FENCE);
		chunk.setBlocksUpward(x, skywalkAt + 2, z + 2, Material.IRON_FENCE);
		chunk.setBlocksUpward(x + 3, skywalkAt + 2, z + 2, Material.IRON_FENCE);
	}

	private void generateSkyWalkWE(RealBlocks chunk, int x, int z, int skywalkAt) {
		chunk.setBlocks(x, x + 6, skywalkAt, z, z + 4, ceilingMaterial);
		chunk.setBlocks(x, x + 6, skywalkAt + 1, z, z + 1, Material.IRON_FENCE);
		chunk.setBlocks(x, x + 6, skywalkAt + 1, z + 3, z + 4, Material.IRON_FENCE);
		chunk.setBlocksUpward(x + 2, skywalkAt + 2, z, Material.IRON_FENCE);
		chunk.setBlocksUpward(x + 2, skywalkAt + 2, z + 3, Material.IRON_FENCE);
	}
}