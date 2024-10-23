<template>
    <v-data-table class="blue-header" :headers="headers" :fixed-header="true" v-if="!loading" :loading="loading"
        density="compact" :items="items" :items-per-page="-1" hover="true" @click:row="handleClick">
        <template v-slot:[`item.actions`]="{ item }">
            <template v-if="showAction1">
                <v-btn size="x-small" color="#FFEBEE" @click="action1PItem(item)">
                    {{ action1 }}
                </v-btn>
            </template>

            <v-spacer />

            <template v-if="showAction2">
                <v-btn size="x-small" color="#E0F2F1" @click="action2PItem(item)">
                    {{ action2 }}
                </v-btn>
            </template>
        </template>

        <template v-slot:[`item.action1`]="{ item }">
            <template v-if="showAction3">
                <v-btn size="x-small" color="#E8F5E9" @click="action1PItem(item)">
                    {{ action3 }}
                </v-btn>
            </template>

            <v-spacer />

            <template v-if="showAction4">
                <v-btn size="x-small" color="#FFF8E1" @click="action2PItem(item)">
                    {{ action4 }}
                </v-btn>
            </template>
        </template>
    </v-data-table>
</template>

<script>
import { defineComponent, ref } from 'vue';
import { VDataTable }  from "vuetify/lib/components";

export default defineComponent({
    components: {
        VDataTable,
    },

    props: {
        headers: Object,
        items: Object,
        action1: String,
        action2: String,
        action3: String,
        action4: String,

        loading: {
            type: Boolean,
            default: false
        },

        showAction1: {
            type: Boolean,
            default: false
        },

        showAction2: {
            type: Boolean,
            default: false
        },

        showAction3: {
            type: Boolean,
            default: false
        },

        showAction4: {
            type: Boolean,
            default: false
        }
    },

    setup(props, { emit }) {
        const action1PItem = (item) => {
            emit('action1Item', item);
        };

        const action2PItem = (item) => {
            emit('action2Item', item);
        };

        const loading = ref(false);

        const handleClick = (item) => {
            highlightClickedRow(item);
            viewDetails(item);
        };

        const highlightClickedRow = (item) => {
            const rowElement = document.querySelector(`[data-item-id="${item.id}"]`);
            if (rowElement) {
                rowElement.classList.add('highlight');
            }
        };

        const viewDetails = (item) => {
            console.log("Voir les détails pour : ", item);
        };

        return {
            action1PItem,
            action2PItem,
            loading,
            handleClick,
            highlightClickedRow,
            viewDetails
        };
    }
});
</script>

<style scoped>
.highlight {
    background-color: red;
    /* Couleur de surbrillance */
}
</style>