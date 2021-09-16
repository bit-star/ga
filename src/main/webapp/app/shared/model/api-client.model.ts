import { ILinkSystem } from '@/shared/model/link-system.model';

export interface IApiClient {
  id?: number;
  name?: string | null;
  apiKey?: string | null;
  apiSecret?: string | null;
  enable?: string | null;
  linkSystem?: ILinkSystem | null;
}

export class ApiClient implements IApiClient {
  constructor(
    public id?: number,
    public name?: string | null,
    public apiKey?: string | null,
    public apiSecret?: string | null,
    public enable?: string | null,
    public linkSystem?: ILinkSystem | null
  ) {}
}
