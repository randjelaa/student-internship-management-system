export interface CvResponse {
  id: number;
  photoUrl: string;
  summary: string;
  createdAt: string;
  updatedAt: string;

  educations: Education[];
  experiences: Experience[];
  skills: Skill[];
  languages: Language[];
  interests: Interest[];
}

export interface Education {
  id: number;
  institution: string;
  degree: string;
  fieldOfStudy: string;
  startYear: number;
  endYear: number;
}

export interface Experience {
  id: number;
  companyName: string;
  position: string;
  description: string;
  startDate: string; 
  endDate: string;
}

export interface Skill {
  id: number;
  skillName: string;
  skillLevel: string;
}

export interface Language {
  id: number;
  languageName: string;
  level: string;
}

export interface Interest {
  id: number;
  interestName: string;
}
