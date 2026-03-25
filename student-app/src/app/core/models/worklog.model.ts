export interface WorkLogResponse {
  id: number;
  studentId: number;
  internshipId: number;
  startDate: string;
  endDate: string;
  description: string;
}

export interface CreateWorkLogRequest {
  internshipId: number;
  startDate: string;
  endDate: string;
  description: string;
}