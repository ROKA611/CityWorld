package me.daddychurchill.CityWorld.Plats;

import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.HouseFactory;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class HouseLot extends IsolatedLot {

	public HouseLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.STRUCTURE;
	}

	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {
		
		// ground please
		if (generator.settings.includeDecayedNature)
			chunk.setLayer(generator.sidewalkLevel, sandId);
		else
			chunk.setLayer(generator.sidewalkLevel, generator.oreProvider.surfaceId);
	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {

		// now make a house
		int floors = HouseFactory.generateHouse(chunk, context, chunkRandom, generator.sidewalkLevel + 1, 2);
		
		// not a happy place?
		if (generator.settings.includeDecayedBuildings) {
			destroyBuilding(generator, chunk, generator.sidewalkLevel + 1, floors);
		}
	}

}