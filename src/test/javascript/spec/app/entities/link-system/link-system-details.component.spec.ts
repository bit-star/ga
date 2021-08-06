/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import LinkSystemDetailComponent from '@/entities/link-system/link-system-details.vue';
import LinkSystemClass from '@/entities/link-system/link-system-details.component';
import LinkSystemService from '@/entities/link-system/link-system.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('LinkSystem Management Detail Component', () => {
    let wrapper: Wrapper<LinkSystemClass>;
    let comp: LinkSystemClass;
    let linkSystemServiceStub: SinonStubbedInstance<LinkSystemService>;

    beforeEach(() => {
      linkSystemServiceStub = sinon.createStubInstance<LinkSystemService>(LinkSystemService);

      wrapper = shallowMount<LinkSystemClass>(LinkSystemDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { linkSystemService: () => linkSystemServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLinkSystem = { id: 123 };
        linkSystemServiceStub.find.resolves(foundLinkSystem);

        // WHEN
        comp.retrieveLinkSystem(123);
        await comp.$nextTick();

        // THEN
        expect(comp.linkSystem).toBe(foundLinkSystem);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLinkSystem = { id: 123 };
        linkSystemServiceStub.find.resolves(foundLinkSystem);

        // WHEN
        comp.beforeRouteEnter({ params: { linkSystemId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.linkSystem).toBe(foundLinkSystem);
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
