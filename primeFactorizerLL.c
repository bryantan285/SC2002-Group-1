//GIVEN A POSITIVE INTEGER, PERFORM PRIME FACTORIZATION OF THE NUMBER, PUT THE VALUES INTO A LINKED LIST
#include <stdio.h>
#include <stdlib.h>

typedef struct ListNode{ //structure of a LL node
    int value;
    struct ListNode *next;
} node;

typedef struct LinkedList { //linked list meta data
    node *head; //points to head node
    node *last; //points to last node
} ll;

void printList(ll *list){
    node *head = list->head;
    while (head != NULL){
        printf("%d ", head->value);
        head = head->next;
    }
}

void addNode(ll *linkedList, int index, int value) {
    //assumes the list is already created and has nodes
    node *head = linkedList->head;
    node *prev = head, *cur; //new node is inserted between prev and cur
    if (prev->next == NULL) {
        prev->next = malloc(sizeof(node));
        prev->next->value = value;
        prev->next->next = NULL;
    } else {
        for (int i = 0; i < index-1; i++){
            prev = prev->next;
        }
        cur = prev->next;
        prev->next = malloc(sizeof(node));
        prev->next->value = value;
        prev->next->next = cur;
    }
}

int main() {
    //create linked list
    ll *llHead = malloc(sizeof(ll));
    llHead->head = malloc(sizeof(node));
    llHead->head->value = 69;
    addNode(llHead, 1, 5);
    addNode(llHead, 1, 4);
    printList(llHead);
    return 0;
}