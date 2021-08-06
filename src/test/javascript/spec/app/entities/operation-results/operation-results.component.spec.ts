/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import OperationResultsComponent from '@/entities/operation-results/operation-results.vue';
import OperationResultsClass from '@/entities/operation-results/operation-results.component';
import OperationResultsService from '@/entities/operation-results/operation-results.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('OperationResults Management Component', () => {
    let wrapper: Wrapper<OperationResultsClass>;
    let comp: OperationResultsClass;
    let operationResultsServiceStub: SinonStubbedInstance<OperationResultsService>;

    beforeEach(() => {
      operationResultsServiceStub = sinon.createStubInstance<OperationResultsService>(OperationResultsService);
      operationResultsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<OperationResultsClass>(OperationResultsComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          operationResultsService: () => operationResultsServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      operationResultsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllOperationResultss();
      await comp.$nextTick();

      // THEN
      expect(operationResultsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.operationResults[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      operationResultsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeOperationResults();
      await comp.$nextTick();

      // THEN
      expect(operationResultsServiceStub.delete.called).toBeTruthy();
      expect(operationResultsServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
