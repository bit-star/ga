/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TopboxesDetailComponent from '@/entities/topboxes/topboxes-details.vue';
import TopboxesClass from '@/entities/topboxes/topboxes-details.component';
import TopboxesService from '@/entities/topboxes/topboxes.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Topboxes Management Detail Component', () => {
    let wrapper: Wrapper<TopboxesClass>;
    let comp: TopboxesClass;
    let topboxesServiceStub: SinonStubbedInstance<TopboxesService>;

    beforeEach(() => {
      topboxesServiceStub = sinon.createStubInstance<TopboxesService>(TopboxesService);

      wrapper = shallowMount<TopboxesClass>(TopboxesDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { topboxesService: () => topboxesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTopboxes = { id: 123 };
        topboxesServiceStub.find.resolves(foundTopboxes);

        // WHEN
        comp.retrieveTopboxes(123);
        await comp.$nextTick();

        // THEN
        expect(comp.topboxes).toBe(foundTopboxes);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTopboxes = { id: 123 };
        topboxesServiceStub.find.resolves(foundTopboxes);

        // WHEN
        comp.beforeRouteEnter({ params: { topboxesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.topboxes).toBe(foundTopboxes);
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
