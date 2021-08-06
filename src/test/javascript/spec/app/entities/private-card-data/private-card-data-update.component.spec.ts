/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import PrivateCardDataUpdateComponent from '@/entities/private-card-data/private-card-data-update.vue';
import PrivateCardDataClass from '@/entities/private-card-data/private-card-data-update.component';
import PrivateCardDataService from '@/entities/private-card-data/private-card-data.service';

import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';

import DdUserService from '@/entities/dd-user/dd-user.service';

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
  describe('PrivateCardData Management Update Component', () => {
    let wrapper: Wrapper<PrivateCardDataClass>;
    let comp: PrivateCardDataClass;
    let privateCardDataServiceStub: SinonStubbedInstance<PrivateCardDataService>;

    beforeEach(() => {
      privateCardDataServiceStub = sinon.createStubInstance<PrivateCardDataService>(PrivateCardDataService);

      wrapper = shallowMount<PrivateCardDataClass>(PrivateCardDataUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          privateCardDataService: () => privateCardDataServiceStub,

          publicCardDataService: () => new PublicCardDataService(),

          ddUserService: () => new DdUserService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.privateCardData = entity;
        privateCardDataServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(privateCardDataServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.privateCardData = entity;
        privateCardDataServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(privateCardDataServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPrivateCardData = { id: 123 };
        privateCardDataServiceStub.find.resolves(foundPrivateCardData);
        privateCardDataServiceStub.retrieve.resolves([foundPrivateCardData]);

        // WHEN
        comp.beforeRouteEnter({ params: { privateCardDataId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.privateCardData).toBe(foundPrivateCardData);
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
