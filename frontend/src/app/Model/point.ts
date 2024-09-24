import { Contribution } from "./contribution";
import { Municipality } from "./municipality";

export interface Point {
    id: number;
    municipality: Municipality;
    contributions: Contribution[];
    position: {
      latitude: number;
      longitude: number;
    };
  }