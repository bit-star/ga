import { IPublicCardData } from '@/shared/model/public-card-data.model';

export interface IConfirmCard {
  id?: number;
  text?: string | null;
  finish?: string | null;
  userId?: string | null;
  link?: string | null;
  md1?: string | null;
  publicCardData?: IPublicCardData | null;
}

export class ConfirmCard implements IConfirmCard {
  constructor(
    public id?: number,
    public text?: string | null,
    public finish?: string | null,
    public userId?: string | null,
    public link?: string | null,
    public md1?: string | null,
    public publicCardData?: IPublicCardData | null
  ) {}
}
