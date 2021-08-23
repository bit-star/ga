import { HttpMethod } from '@/shared/model/enumerations/http-method.model';
import { ApiDirection } from '@/shared/model/enumerations/api-direction.model';
export interface IApiInvokeLog {
  id?: number;
  login?: string | null;
  apiName?: string | null;
  method?: HttpMethod | null;
  direction?: ApiDirection | null;
  httpStatusCode?: number | null;
  time?: Date | null;
  reqeustData?: string | null;
  responseData?: string | null;
  ok?: boolean | null;
}

export class ApiInvokeLog implements IApiInvokeLog {
  constructor(
    public id?: number,
    public login?: string | null,
    public apiName?: string | null,
    public method?: HttpMethod | null,
    public direction?: ApiDirection | null,
    public httpStatusCode?: number | null,
    public time?: Date | null,
    public reqeustData?: string | null,
    public responseData?: string | null,
    public ok?: boolean | null
  ) {
    this.ok = this.ok ?? false;
  }
}
