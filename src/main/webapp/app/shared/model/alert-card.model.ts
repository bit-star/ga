import { IPublicCardData } from '@/shared/model/public-card-data.model';

export interface IAlertCard {
  id?: number;
  text?: string | null;
  userId?: string | null;
  publicCardData?: IPublicCardData | null;
}

export class AlertCard implements IAlertCard {
  constructor(
    public id?: number,
    public text?: string | null,
    public userId?: string | null,
    public publicCardData?: IPublicCardData | null
  ) {}
}
