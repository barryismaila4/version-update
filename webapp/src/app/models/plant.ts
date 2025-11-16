import { User } from "./user";
import { Notification } from "./notification";
export interface Plant {
  id: number;
  name: string;
  description?: string;
  plantType?: string;
  plantingDate?: string;
  lastWatered?: string;
  wateringFrequencyDays?: number;
  createdAt?: string;
  updatedAt?: string;
  user?: User;
  notifications?: Notification[];
}