/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AlertCardDetailComponent from '@/entities/alert-card/alert-card-details.vue';
import AlertCardClass from '@/entities/alert-card/alert-card-details.component';
import AlertCardService from '@/entities/alert-card/alert-card.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AlertCard Management Detail Component', () => {
    let wrapper: Wrapper<AlertCardClass>;
    let comp: AlertCardClass;
    let alertCardServiceStub: SinonStubbedInstance<AlertCardService>;

    beforeEach(() => {
      alertCardServiceStub = sinon.createStubInstance<AlertCardService>(AlertCardService);

      wrapper = shallowMount<AlertCardClass>(AlertCardDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { alertCardService: () => alertCardServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAlertCard = { id: 123 };
        alertCardServiceStub.find.resolves(foundAlertCard);

        // WHEN
        comp.retrieveAlertCard(123);
        await comp.$nextTick();

        // THEN
        expect(comp.alertCard).toBe(foundAlertCard);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAlertCard = { id: 123 };
        alertCardServiceStub.find.resolves(foundAlertCard);

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
