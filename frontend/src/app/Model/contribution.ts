export interface Contribution {
    id: number;
    title: string;
    description: string;
    type: string;
    state: string;
    multimedia: string[]; // Percorsi ai file multimediali
    author: {
      id: number;
      name: string;
    };
  }