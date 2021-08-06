/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import GroupMembersDetailComponent from '@/entities/group-members/group-members-details.vue';
import GroupMembersClass from '@/entities/group-members/group-members-details.component';
import GroupMembersService from '@/entities/group-members/group-members.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('GroupMembers Management Detail Component', () => {
    let wrapper: Wrapper<GroupMembersClass>;
    let comp: GroupMembersClass;
    let groupMembersServiceStub: SinonStubbedInstance<GroupMembersService>;

    beforeEach(() => {
      groupMembersServiceStub = sinon.createStubInstance<GroupMembersService>(GroupMembersService);

      wrapper = shallowMount<GroupMembersClass>(GroupMembersDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { groupMembersService: () => groupMembersServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundGroupMembers = { id: 123 };
        groupMembersServiceStub.find.resolves(foundGroupMembers);

        // WHEN
        comp.retrieveGroupMembers(123);
        await comp.$nextTick();

        // THEN
        expect(comp.groupMembers).toBe(foundGroupMembers);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundGroupMembers = { id: 123 };
        groupMembersServiceStub.find.resolves(foundGroupMembers);

        // WHEN
        comp.beforeRouteEnter({ params: { groupMembersId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.groupMembers).toBe(foundGroupMembers);
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
