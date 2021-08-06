/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import LinkSystemUpdateComponent from '@/entities/link-system/link-system-update.vue';
import LinkSystemClass from '@/entities/link-system/link-system-update.component';
import LinkSystemService from '@/entities/link-system/link-system.service';

import WorkflowTemplateService from '@/entities/workflow-template/workflow-template.service';

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
  describe('LinkSystem Management Update Component', () => {
    let wrapper: Wrapper<LinkSystemClass>;
    let comp: LinkSystemClass;
    let linkSystemServiceStub: SinonStubbedInstance<LinkSystemService>;

    beforeEach(() => {
      linkSystemServiceStub = sinon.createStubInstance<LinkSystemService>(LinkSystemService);

      wrapper = shallowMount<LinkSystemClass>(LinkSystemUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          linkSystemService: () => linkSystemServiceStub,

          workflowTemplateService: () => new WorkflowTemplateService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.linkSystem = entity;
        linkSystemServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(linkSystemServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.linkSystem = entity;
        linkSystemServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(linkSystemServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLinkSystem = { id: 123 };
        linkSystemServiceStub.find.resolves(foundLinkSystem);
        linkSystemServiceStub.retrieve.resolves([foundLinkSystem]);

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
