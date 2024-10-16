export interface Task {
  id: number;
  name: string;
  description: string;
  done: boolean;
  list_id: number;
  create_timestamp: number;
  update_timestamp: number;
}
