export interface CvResponse {
  id: number;
  photoUrl: string;
  summary: string;

  educations: Education[];
  experiences: Experience[];
  skills: Skill[];
  languages: Language[];
  interests: Interest[];
}

export interface Education {
  institution: string;
  degree: string;
  fieldOfStudy: string;
  startYear: number;
  endYear: number;
}

export interface Experience {
  companyName: string;
  position: string;
  description: string;
  startDate: string;
  endDate: string;
}

export interface Skill {
  skillName: string;
  skillLevel: string;
}

export interface Language {
  languageName: string;
  level: string;
}

export interface Interest {
  interestName: string;
}