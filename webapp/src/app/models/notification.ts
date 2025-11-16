import { Plant } from "./plant";
import { User } from "./user";

export interface Notification {
  id: number;
  title: string;
  description?: string;
  notificationDate: string;
  isRead: boolean;
  notificationType?: string;
  createdAt?: string;
  user?: User;
  plant?: Plant;
}