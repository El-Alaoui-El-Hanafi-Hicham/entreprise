



export interface TaskModule {

  id: number;
  title: string;
  description?: string;
  startDate: string;  // e.g. "2025-08-26T10:00:00"
  dueDate: string;    // e.g. "2025-08-26T15:00:00"
  manager?: { id:number, firstName: string; lastName: string };
  assignees?: { id:number, firstName: string; lastName: string }[];
  createdBy:{ id:number, firstName: string; lastName: string };

 }
