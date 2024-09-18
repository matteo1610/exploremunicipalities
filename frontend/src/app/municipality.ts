export interface Municipality {
    id: number;
    name: string;
    province: string;
    identityPoint: {
      latitude: number;
      longitude: number;
    };
  }