/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import AlertCardUpdateComponent from '@/entities/alert-card/alert-card-update.vue';
import AlertCardClass from '@/entities/alert-card/alert-card-update.component';
import AlertCardService from '@/entities/alert-card/alert-card.service';

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
  describe('AlertCard Management Update Component', () => {
    let wrapper: Wrapper<AlertCardClass>;
    let comp: AlertCardClass;
    let alertCardServiceStub: SinonStubbedInstance<AlertCardService>;

    beforeEach(() => {
      alertCardServiceStub = sinon.createStubInstance<AlertCardService>(AlertCardService);

      wrapper = shallowMount<AlertCardClass>(AlertCardUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertCardService: () => alertCardServiceStub,

          publicCardDataService: () => new PublicCardDataService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.alertCard = entity;
        alertCardServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(alertCardServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.alertCard = entity;
        alertCardServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(alertCardServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAlertCard = { id: 123 };
        alertCardServiceStub.find.resolves(foundAlertCard);
        alertCardServiceStub.retrieve.resolves([foundAlertCard]);

        // WHEN
        comp.beforeRouteEnter({ params: { alertCardId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.alertCard).toBe(foundAlertCard);
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
