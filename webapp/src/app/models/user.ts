
import { Notification } from './notification';
import { Plant } from './plant';

export interface User {
  id: number;
  username: string;
  email: string;
  password: string;
  role: string;
  plants: Plant[];
  notifications: Notification[];
}