/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import OperationResultsDetailComponent from '@/entities/operation-results/operation-results-details.vue';
import OperationResultsClass from '@/entities/operation-results/operation-results-details.component';
import OperationResultsService from '@/entities/operation-results/operation-results.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('OperationResults Management Detail Component', () => {
    let wrapper: Wrapper<OperationResultsClass>;
    let comp: OperationResultsClass;
    let operationResultsServiceStub: SinonStubbedInstance<OperationResultsService>;

    beforeEach(() => {
      operationResultsServiceStub = sinon.createStubInstance<OperationResultsService>(OperationResultsService);

      wrapper = shallowMount<OperationResultsClass>(OperationResultsDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { operationResultsService: () => operationResultsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOperationResults = { id: 123 };
        operationResultsServiceStub.find.resolves(foundOperationResults);

        // WHEN
        comp.retrieveOperationResults(123);
        await comp.$nextTick();

        // THEN
        expect(comp.operationResults).toBe(foundOperationResults);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOperationResults = { id: 123 };
        operationResultsServiceStub.find.resolves(foundOperationResults);

        // WHEN
        comp.beforeRouteEnter({ params: { operationResultsId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.operationResults).toBe(foundOperationResults);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
