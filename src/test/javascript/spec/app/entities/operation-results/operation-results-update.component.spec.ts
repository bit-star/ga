/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import OperationResultsUpdateComponent from '@/entities/operation-results/operation-results-update.vue';
import OperationResultsClass from '@/entities/operation-results/operation-results-update.component';
import OperationResultsService from '@/entities/operation-results/operation-results.service';

import DdUserService from '@/entities/dd-user/dd-user.service';

import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('OperationResults Management Update Component', () => {
    let wrapper: Wrapper<OperationResultsClass>;
    let comp: OperationResultsClass;
    let operationResultsServiceStub: SinonStubbedInstance<OperationResultsService>;

    beforeEach(() => {
      operationResultsServiceStub = sinon.createStubInstance<OperationResultsService>(OperationResultsService);

      wrapper = shallowMount<OperationResultsClass>(OperationResultsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          operationResultsService: () => operationResultsServiceStub,

          ddUserService: () => new DdUserService(),

          publicCardDataService: () => new PublicCardDataService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.operationResults = entity;
        operationResultsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(operationResultsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.operationResults = entity;
        operationResultsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(operationResultsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOperationResults = { id: 123 };
        operationResultsServiceStub.find.resolves(foundOperationResults);
        operationResultsServiceStub.retrieve.resolves([foundOperationResults]);

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
