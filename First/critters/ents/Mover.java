package ents;

import critters.CritterInfo;
import critters.Critter.Action;

interface Mover { public Action getMove(CritterInfo info); }