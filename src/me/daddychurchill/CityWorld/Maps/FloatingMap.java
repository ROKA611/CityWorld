package me.daddychurchill.CityWorld.Maps;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.FloatingBlimpLot;
import me.daddychurchill.CityWorld.Plats.FloatingNothingLot;
import me.daddychurchill.CityWorld.Plats.FloatingRoadLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.RoadLot;
import me.daddychurchill.CityWorld.Plats.RoundaboutStatueLot;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class FloatingMap extends PlatMap {

	public FloatingMap(WorldGenerator aGenerator, SupportChunk typicalChunk, int aOriginX, int aOriginZ) {
		super(aGenerator, typicalChunk, aOriginX, aOriginZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void populateLots(SupportChunk typicalChunk) {

		// assume everything is natural for the moment
		context = generator.floatingContext;
		context.populateMap(generator, this);
		
		// place and validate the roads
		if (generator.settings.includeRoads) {
			populateRoads(typicalChunk);
			validateRoads(typicalChunk);

			// place the buildings
			if (generator.settings.includeBuildings) {
	
				// recalculate the context based on the "natural-ness" of the platmap
				context = getContext();
				context.populateMap(generator, this);
				
				//TODO need to remove isolated non-nature
				
				// find blimp moorings
				for (int x = 0; x < Width; x++) {
					for (int z = 0; z < Width; z++) {
						if (needBlimpLot(x, z))
							setLot(x, z, new FloatingBlimpLot(this, originX + x, originZ + z));
					}
				}
			}
		}
		
		//TODO: nature shouldn't place its special lots until this phase and then only if the lot is surrounded by nature
		
		// recycle all the remaining holes
		for (int x = 0; x < Width; x++) {
			for (int z = 0; z < Width; z++) {
				if (isEmptyLot(x, z))
					recycleLot(x, z);
			}
		}
	}
	
	private boolean needBlimpLot(int x, int z) {
		if (isNaturalLot(x, z)) {
			return isStructureLot(x - 1, z) || isStructureLot(x + 1, z) ||
				   isStructureLot(x, z - 1) || isStructureLot(x, z + 1);
		} else
			return false;
	}

	@Override
	protected PlatLot createNaturalLot(int x, int z) {
		return new FloatingNothingLot(this, originX + x, originZ + z);
	}

	@Override
	protected PlatLot createRoadLot(int x, int z, boolean roundaboutPart) {
		return new FloatingRoadLot(this, originX + x, originZ + z, generator.connectedKeyForPavedRoads, roundaboutPart);
	}

	@Override
	protected PlatLot createRoundaboutStatueLot(int x, int z) {
		return new RoundaboutStatueLot(this, originX + x, originZ + z);
		//return new FloatingRoundaboutCenterLot(this, originX + x, originZ + z);
	}

	protected void populateRoads(SupportChunk typicalChunk) {
		
		// place the big four
		placeIntersection(typicalChunk, RoadLot.PlatMapRoadInset - 1, RoadLot.PlatMapRoadInset - 1);
		placeIntersection(typicalChunk, RoadLot.PlatMapRoadInset - 1, Width - RoadLot.PlatMapRoadInset);
		placeIntersection(typicalChunk, Width - RoadLot.PlatMapRoadInset, RoadLot.PlatMapRoadInset - 1);
		placeIntersection(typicalChunk, Width - RoadLot.PlatMapRoadInset, Width - RoadLot.PlatMapRoadInset);
	}
}
