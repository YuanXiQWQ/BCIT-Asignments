# Lab 8 - 在 C 中实现链表

**截止日期：** 2024年11月24日

## 目标  
本实验旨在帮助学生全面理解循环链表及其在C语言编程中的实际应用。通过完成实验，学生将学会循环链表的结构和功能，并能动手编写代码，实现节点的创建、插入、删除和显示。学生将掌握循环链表在实际场景中的应用，例如模拟交通控制系统中信号灯的顺序切换。本实验还将提高学生的问题解决和调试能力，确保学生能够编写高效、无错误的代码，同时有效管理内存。

---

### 步骤 1：定义节点结构与函数原型
**文件：** `circular_node.h`  
此头文件将包含节点结构的定义和函数原型。
```c
#ifndef CIRCULAR_NODE_H
#define CIRCULAR_NODE_H

struct Node {
    char signal[10];
    int duration;
    struct Node* next;
};

struct Node* createNode(const char* signal, int duration);
void insertAtEnd(struct Node** head, const char* signal, int duration);
void deleteNode(struct Node** head, const char* signal);
void displayList(struct Node* head);

#endif
```
### 步骤 2：实现函数
**文件：** `circular_node.c`  
此源文件将包含 `circular_node.h` 中声明的函数实现。
- **`createNode`**:  
	- 作用：根据指定的信号颜色和持续时间创建一个新节点，并初始化指针指向自身。
```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "circular_node.h"

struct Node* createNode(const char* signal, int duration) {
    struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
    strcpy(newNode->signal, signal);
    newNode->duration = duration;
    newNode->next = newNode;
    return newNode;
}
```
- **`insertAtEnd`**:  
	-  作用：在循环链表末尾插入一个新节点，并更新指针以维持循环结构。
```c
// Function to insert a node at the end of the list
void insertAtEnd(struct Node** head, const char* signal, int duration) {
    struct Node* newNode = createNode(signal, duration); // Create a new node
    if (*head == NULL) { // If the list is empty
        *head = newNode; // Set the new node as the head
        return;
    }
    struct Node* temp = *head; // Temporary pointer to traverse the list
    while (temp->next != *head) { // Traverse to the last node
        temp = temp->next;
    }
    temp->next = newNode; // Set the last node's next pointer to the new node
    newNode->next = *head; // Set the new node's next pointer to the head
}
```
- **`deleteNode`**:  
	- 作用：根据给定的信号颜色删除链表中的节点，并处理头节点、尾节点和中间节点的删除。
```c
// Function to delete a node with a given signal color
void deleteNode(struct Node** head, const char* signal) {
    if (*head == NULL) return;

    struct Node *temp = *head, *prev = NULL;

    // If the head node itself holds the signal to be deleted
    if (strcmp(temp->signal, signal) == 0 && temp->next == *head) {
        *head = NULL;
        free(temp);
        return;
    }

    // If the head node itself holds the signal to be deleted and there are more nodes
    if (strcmp(temp->signal, signal) == 0) {
        while (temp->next != *head) {
            temp = temp->next;
        }
        temp->next = (*head)->next;
        free(*head);
        *head = temp->next;
        return;
    }

    // Search for the signal to be deleted, keeping track of the previous node
    while (temp->next != *head && strcmp(temp->signal, signal) != 0) {
        prev = temp;
        temp = temp->next;
    }

    // If the signal was not present in the list
    if (strcmp(temp->signal, signal) != 0) return;

    // Unlink the node from the list
    prev->next = temp->next;
    free(temp); // Free the memory of the node to be deleted
}
```
- **`displayList`**:  
	- 作用：显示循环链表，打印每个节点的信号颜色和持续时间，最后指出链表的循环特性。
```c
// Function to display the list
void displayList(struct Node* head) {
    if (head == NULL) return;

    struct Node* temp = head;
    do {
        printf("%s (%d seconds) -> ", temp->signal, temp->duration); // Print the signal color and duration of each node
        temp = temp->next;
    } while (temp != head);
    printf("%s (head)\n", head->signal); // Indicate the circular nature of the list
}
```
### 步骤 3：演示循环链表操作
**文件：** `main.c`  
主函数将演示以下操作：
1. **初始化头节点**：  
   将链表头节点设为 `NULL`。
2. **插入节点**：
   - 插入“Red”信号，持续 30 秒。  
   - 插入“Green”信号，持续 45 秒。  
   - 插入“Yellow”信号，持续 10 秒。
3. **显示链表**：  
   显示链表中信号灯及其持续时间的顺序。
4. **删除节点**：  
   删除“Green”信号节点。
5. **更新后显示**：  
   再次显示更新后的链表。

### 步骤 4：创建 Makefile
**文件：** `Makefile`  
根据 Week 4 以来的模板完成 `Makefile`，确保其包含编译和链接源文件的规则。

## 编译与运行
使用以下命令编译和运行程序：  
```bash
make
./circular_linkedlist
```
## 期望输出
```plaintext
Circular linked list (traffic signals):
Red (30 seconds) -> Green (45 seconds) -> Yellow (10 seconds) -> Red (head)

Circular linked list after deletion:
Red (30 seconds) -> Yellow (10 seconds) -> Red (head)
```
## 额外活动

作为额外活动，您可以创建新功能或对现有代码进行小改动。以下是一些建议：

1. **添加新的信号：**
   - 在循环链表中插入一个不同颜色和持续时间的新交通信号。

2. **修改信号持续时间：**
   - 更新链表中现有交通信号的持续时间。

3. **循环遍历信号：**
   - 实现一个函数，用于模拟依次循环显示交通信号及其持续时间。

您可以选择以上任何一个活动，也可以自己创造性的改进循环链表的实现。

## 提交内容

1. **源代码文件：**
   - `circular_node.h`
   - `circular_node.c`
   - `main.c`
   - `Makefile`

2. **额外活动（可选）：**
   - 如果完成了额外活动，请提交修改后的代码及新增功能的描述。


### 评分标准

1. **给定代码的完整性（8 分）：**
   - 代码正确实现了指定的循环链表操作。
   - 程序能无错误地编译和运行。
   - 输出与提供的示例相匹配。

2. **`main.c` 和 `Makefile` 的完成度（2 分）：**
   - 按照指示完成 `main.c` 文件。
   - 正确设置 `Makefile` 以编译和链接源文件。

3. **附加活动（2 分）：**
   - 成功实现附加活动。
   - 清晰说明新增功能或所做更改的描述。

