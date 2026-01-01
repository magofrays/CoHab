<template>
  <div class="min-h-screen p-6">
    <div class="max-w-7xl mx-auto">
      <!-- Header -->
      <div class="text-center mb-12">
        <h1 class="text-4xl font-bold text-gray-800 mb-4">Добро пожаловать в семейный органайзер!</h1>
        <p class="text-xl text-gray-600">Управляйте задачами и членами семьи в одном месте</p>
      </div>

      <!-- Loading Indicator -->
      <div v-if="loading" class="fixed top-4 right-4 bg-blue-500 text-white px-4 py-2 rounded-lg shadow-lg">
        Загрузка...
      </div>

      <!-- Main Content Grid -->
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <!-- Tasks Section -->
        <div class="backdrop-blur rounded-2xl shadow-xl p-8 border border-gray-200 card-hover">
          <div class="flex items-center justify-between mb-6">
            <h2 class="text-2xl font-semibold text-gray-800">Задачи семьи</h2>
            <span class="bg-blue-100 text-blue-800 text-sm font-medium px-3 py-1 rounded-full">{{ tasks.length }} задач</span>
          </div>

          <div class="space-y-4">
            <div v-if="tasks.length === 0" class="text-center py-8">
              <p class="text-gray-500">Задачи пока не добавлены</p>
            </div>
            <div v-else v-for="task in tasks" :key="task.id" class="p-4 bg-white rounded-lg border border-gray-200 hover:bg-gray-50 transition-colors">
              <!-- Task content -->
              <div class="flex justify-between items-start">
                <div>
                  <h3 class="font-medium text-gray-800">{{ task.title || 'Без названия' }}</h3>
                  <p class="text-sm text-gray-600 mt-1">{{ task.description || 'Нет описания' }}</p>
                </div>
                <span :class="task.completed ? 'bg-green-100 text-green-800' : 'bg-yellow-100 text-yellow-800'" 
                      class="text-xs font-medium px-2 py-1 rounded-full">
                  {{ task.completed ? 'Выполнено' : 'В процессе' }}
                </span>
              </div>
              <div class="flex justify-between items-center mt-3 text-sm text-gray-500">
                <span>{{ task.assignedTo || 'Не назначено' }}</span>
                <span>{{ task.dueDate ? formatDate(task.dueDate) : 'Без срока' }}</span>
              </div>
              <div class="flex gap-2 mt-3">
                <button @click="toggleTask(task.id)" class="text-xs bg-blue-500 text-white px-2 py-1 rounded">
                  {{ task.completed ? 'Возобновить' : 'Завершить' }}
                </button>
                <button @click="deleteTask(task.id)" class="text-xs bg-red-500 text-white px-2 py-1 rounded">
                  Удалить
                </button>
              </div>
            </div>
          </div>

          <div class="mt-6">
            <button @click="loadTasks" class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors font-medium w-full">
              Обновить задачи
            </button>
          </div>
        </div>

        <!-- Family Members Section -->
        <div class="backdrop-blur rounded-2xl shadow-xl p-8 border border-gray-200 card-hover">
          <div class="flex items-center justify-between mb-6">
            <h2 class="text-2xl font-semibold text-gray-800">Члены семьи</h2>
            <span v-if="userHasFamily" class="bg-green-100 text-green-800 text-sm font-medium px-3 py-1 rounded-full">{{ familyMembers.length }} человек</span>
          </div>

          <!-- Create Family Form -->
          <div v-if="!userHasFamily && showCreateFamilyForm" class="text-center py-8">
            <div class="mb-6">
              <h3 class="text-xl font-semibold text-gray-800 mb-2">У вас еще нет семьи</h3>
              <p class="text-gray-600 mb-6">Создайте семью, чтобы начать управлять задачами и членами семьи вместе!</p>
            </div>
            <div class="max-w-md mx-auto">
              <input 
                v-model="newFamilyName"
                type="text"
                placeholder="Введите название семьи"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 mb-4"
                @keyup.enter="handleCreateFamily"
              />
              <button 
                @click="handleCreateFamily" 
                :disabled="creatingFamily || !newFamilyName.trim()"
                class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors font-medium w-full disabled:bg-gray-400 disabled:cursor-not-allowed"
              >
                <span v-if="creatingFamily">Создание...</span>
                <span v-else>Создать семью</span>
              </button>
            </div>
          </div>

          <!-- Family Members List -->
          <div v-else-if="userHasFamily" class="space-y-4">
            <div v-if="familyMembers.length === 0" class="text-center py-8">
              <p class="text-gray-500">Члены семьи не найдены</p>
            </div>
            <div v-else v-for="member in familyMembers" :key="member.id" class="p-4 bg-white rounded-lg border border-gray-200 hover:bg-gray-50 transition-colors">
              <!-- Member content -->
              <div class="flex items-center space-x-4">
                <div class="w-12 h-12 bg-blue-500 rounded-full flex items-center justify-center text-white font-semibold">
                  {{ (member.personalInfoDto?.firstname?.charAt(0) || '') + (member.personalInfoDto?.lastname?.charAt(0) || '') }}
                </div>
                <div class="flex-1">
                  <h3 class="font-medium text-gray-800">
                    {{ member.personalInfoDto?.firstname || 'Не указано' }} {{ member.personalInfoDto?.lastname || 'Не указано' }}
                  </h3>
                  <p class="text-sm text-gray-600">{{ member.username || 'Без логина' }}</p>
                  <p class="text-xs text-gray-500 mt-1">
                    Дата рождения: {{ member.personalInfoDto?.birthDate ? formatDate(member.personalInfoDto.birthDate) : 'Не указана' }}
                  </p>
                </div>
              </div>
              <div v-if="member.familyDtos && member.familyDtos.length > 0" class="mt-3 pt-3 border-t border-gray-100">
                <span class="text-xs text-gray-500">Семьи:</span>
                <span class="text-sm text-gray-700 ml-2">
                  {{ formatFamilyNames(member.familyDtos) }}
                </span>
              </div>
              <div class="flex gap-2 mt-3">
                <button @click="editMember(member.id)" class="text-xs bg-green-500 text-white px-2 py-1 rounded">
                  Редактировать
                </button>
                <button @click="deleteMember(member.id)" class="text-xs bg-red-500 text-white px-2 py-1 rounded">
                  Удалить
                </button>
              </div>
            </div>
          </div>

          <div v-if="userHasFamily" class="mt-6">
            <button @click="loadFamilyMembers" class="bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-green-700 transition-colors font-medium w-full">
              Обновить список
            </button>
          </div>
        </div>
      </div>

      <!-- Quick Actions -->
      <div class="mt-8 backdrop-blur rounded-2xl shadow-xl p-6 border border-gray-200">
        <h3 class="text-xl font-semibold text-gray-800 mb-4">Быстрые действия</h3>
        <div class="flex flex-wrap gap-4">
          <button @click="showAddTaskForm" class="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700 transition-colors font-medium">
            Новая задача
          </button>
          <button @click="showAddMemberForm" class="bg-green-600 text-white px-6 py-3 rounded-lg hover:bg-green-700 transition-colors font-medium">
            Добавить члена семьи
          </button>
          <button class="bg-gray-600 text-white px-6 py-3 rounded-lg hover:bg-gray-700 transition-colors font-medium">
            Настройки семьи
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="./Home.ts" lang="ts"></script>