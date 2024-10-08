import { License } from "./license";

export interface UserInfo {
    id?: number;
  email: string;
  password: string;
  role: string; // Puoi usare un enum se hai definito GlobalRole come enum
  license: License | null;
  municipalityRole: string;
}