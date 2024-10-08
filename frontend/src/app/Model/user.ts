
import { License } from './license';
import { MunicipalityRole } from './municipalityRole';

export class User {
  id?: number;
  email: string;
  password: string;
  role: string; // Puoi usare un enum se hai definito GlobalRole come enum
  license: License | null;

  constructor(email: string, password: string, role: MunicipalityRole, license: License | null = null) {
    this.email = email;
    this.password = password;
    this.role = role;
    this.license = license;
  }
}