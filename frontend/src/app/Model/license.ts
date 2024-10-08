import { Municipality } from "./municipality";
import { MunicipalityRole } from "./municipalityRole";


export interface License {
    id: number;
    role: MunicipalityRole;
    municipality: Municipality;
  }
  

