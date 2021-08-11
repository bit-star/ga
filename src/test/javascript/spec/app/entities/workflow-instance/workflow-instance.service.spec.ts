/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import WorkflowInstanceService from '@/entities/workflow-instance/workflow-instance.service';
import { WorkflowInstance } from '@/shared/model/workflow-instance.model';
import { WorkflowInstanceStatus } from '@/shared/model/enumerations/workflow-instance-status.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('WorkflowInstance Service', () => {
    let service: WorkflowInstanceService;
    let elemDefault;

    beforeEach(() => {
      service = new WorkflowInstanceService();
      elemDefault = new WorkflowInstance(123, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', WorkflowInstanceStatus.Launch);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a WorkflowInstance', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a WorkflowInstance', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a WorkflowInstance', async () => {
        const returnedFromService = Object.assign(
          {
            form: 'BBBBBB',
            ddCardId: 'BBBBBB',
            title: 'BBBBBB',
            ddCardTemplateId: 'BBBBBB',
            ddCardCallBackKey: 'BBBBBB',
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a WorkflowInstance', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a WorkflowInstance', async () => {
        const patchObject = Object.assign(
          {
            form: 'BBBBBB',
            ddCardCallBackKey: 'BBBBBB',
            status: 'BBBBBB',
          },
          new WorkflowInstance()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a WorkflowInstance', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of WorkflowInstance', async () => {
        const returnedFromService = Object.assign(
          {
            form: 'BBBBBB',
            ddCardId: 'BBBBBB',
            title: 'BBBBBB',
            ddCardTemplateId: 'BBBBBB',
            ddCardCallBackKey: 'BBBBBB',
            status: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of WorkflowInstance', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a WorkflowInstance', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a WorkflowInstance', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
