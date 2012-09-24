package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.WorldGenerator;

public abstract class RuralContext extends DataContext {

	public RuralContext(WorldGenerator generator) {
		super(generator);
	}
	
	@Override
	protected void initialize() {

		oddsOfParks = oddsNeverGoingToHappen;
		oddsOfIsolatedLots = oddsNeverGoingToHappen;
		oddsOfIdenticalBuildingHeights = oddsNeverGoingToHappen;
		oddsOfSimilarBuildingHeights = oddsNeverGoingToHappen;
		oddsOfSimilarBuildingRounding = oddsNeverGoingToHappen;
		oddsOfUnfinishedBuildings = oddsNeverGoingToHappen;
		oddsOfOnlyUnfinishedBasements = oddsNeverGoingToHappen;
		oddsOfMissingRoad = oddsNeverGoingToHappen;
		oddsOfRoundAbouts = oddsVeryUnlikely;
		 
		oddsOfStairWallMaterialIsWallMaterial = oddsNeverGoingToHappen;
		oddsOfBuildingWallInset = oddsNeverGoingToHappen;
		oddsOfFlatWalledBuildings = oddsNeverGoingToHappen;
		oddsOfSimilarInsetBuildings = oddsNeverGoingToHappen;
		rangeOfWallInset = 2;

		maximumFloorsAbove = 1;
		maximumFloorsBelow = 1;
	}

}
