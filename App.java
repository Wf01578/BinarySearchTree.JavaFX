package com.mycompany.bstproject;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;



public class App extends Application {
 @Override

  public void start(Stage primaryStage) {
    BST<Integer> tree = new BST<>();
    

    BorderPane pane = new BorderPane();
    BTView view = new BTView(tree);
    pane.setCenter(view);

    TextField tfKey = new TextField();
    tfKey.setPrefColumnCount(3);
    tfKey.setAlignment(Pos.BASELINE_RIGHT);
    Button btInsert = new Button("Insert");
    Button btDelete = new Button("Delete");
    Button btInorder = new Button("Show Inorder");
    Button btPreorder = new Button("Show Preorder");
    Button btPostorder = new Button("Show Postorder");
    HBox hBox = new HBox(5);
    hBox.getChildren().addAll(new Label("Enter a key: "),
      tfKey, btInsert, btDelete, btInorder, btPreorder, btPostorder);
    hBox.setAlignment(Pos.CENTER);
    pane.setBottom(hBox);

    btInsert.setOnAction(e -> {
      int key = Integer.parseInt(tfKey.getText());
      if (tree.search(key)) {
        view.displayTree();
        view.setStatus(key + " is already in the tree");
      } else {
        tree.insert(key);
        view.displayTree();
        view.setStatus(key + " is inserted in the tree");
      }
      
        
    });

    btDelete.setOnAction(e -> {
      int key = Integer.parseInt(tfKey.getText());
      if (!tree.search(key)) {
        view.displayTree();
        view.setStatus(key + " is not in the tree");
      } else {
        tree.delete(key);
        view.displayTree();
        view.setStatus(key + " is deleted from the tree");
      }
    });

    btInorder.setOnAction(e -> view.displayOrder(BTView.Order.INORDER));
    btPreorder.setOnAction(e -> view.displayOrder(BTView.Order.PREORDER));
    btPostorder.setOnAction(e -> view.displayOrder(BTView.Order.POSTORDER));

    Scene scene = new Scene(pane, 600, 400);
    primaryStage.setTitle("Exercise25_13");
    primaryStage.setScene(scene);
    primaryStage.show();
    
 }
    public static void main(String[] args) {
    launch(args);
  }
  static final class BST<E extends Comparable<E>> extends AbstractTree<E> {
  protected TreeNode<E> root;
  protected int size = 0;

  public BST() {
   
  }

  public BST(E[] objects) {
    for (int i = 0; i < objects.length; i++) {
      insert(objects[i]);
    }
  }

  @Override
  public boolean search(E e) {
    TreeNode<E> current = root;

    while (current != null) {
      if (e.compareTo(current.element) < 0) {
        current = current.left;
      } else if (e.compareTo(current.element) > 0) {
        current = current.right;
      } else {
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean insert(E e) {
    if (root == null) {
      root = createNewNode(e);
    } else {
      TreeNode<E> parent = null;
      TreeNode<E> current = root;
      while (current != null) {
        if (e.compareTo(current.element) < 0){
          parent = current;
          current = current.left;
        } else if (e.compareTo(current.element) > 0) {
          parent = current;
          current = current.right;
        } else {
          return false;
        }
      }

      if (e.compareTo(parent.element) < 0) {
        parent.left = createNewNode(e);
      } else {
        parent.right = createNewNode(e);
      }
    }

    size++;
    return true;
  }

  protected TreeNode<E> createNewNode(E e) {
    return new TreeNode<>(e);
  }

  @Override
  public void inorder() {
    inorder(root);
  }

  protected void inorder(TreeNode<E> root) {
    if (root == null) { return; }
    inorder(root.left);
    System.out.print(root.element + " ");
    inorder(root.right);
  }

  @Override
  public void postorder() {
    postorder(root);
  }

  protected void postorder(TreeNode<E> root) {
    if (root == null) { return; }
    postorder(root.left);
    postorder(root.right);
    System.out.print(root.element + " ");
  }

  @Override
  public void preorder() {
    preorder(root);
  }

  protected void preorder(TreeNode<E> root) {
    if (root == null) { return; }
    System.out.print(root.element + " ");
    preorder(root.left);
    preorder(root.right);
  }

  public List<E> inorderList() {
    List<E> list = new ArrayList<>();
    inorderList(root, list);
    return list;
  }

  private void inorderList(TreeNode<E> node, List<E> list) {
    if (node == null) { return; }
    inorderList(node.left, list);
    list.add(node.element);
    inorderList(node.right, list);
  }

  public List<E> preorderList() {
    List<E> list = new ArrayList<>();
    preorderList(root, list);
    return list;
  }

  private void preorderList(TreeNode<E> node, List<E> list) {
    if (node == null) { return; }
    list.add(node.element);
    preorderList(node.left, list);
    preorderList(node.right, list);
  }

  public List<E> postorderList() {
    List<E> list = new ArrayList<>();
    postorderList(root, list);
    return list;
  }

  private void postorderList(TreeNode<E> node, List<E> list) {
    if (node == null) { return; }
    postorderList(node.left, list);
    postorderList(node.right, list);
    list.add(node.element);
  }

  public static class TreeNode<E extends Comparable<E>> {
    protected E element;
    protected TreeNode<E> left;
    protected TreeNode<E> right;

    public TreeNode(E e) {
      element = e;
    }
  }

  @Override
  public int getSize() {
    return size;
  }

  public TreeNode<E> getRoot() {
    return root;
  }

  public ArrayList<TreeNode<E>> path(E e) {
    ArrayList<TreeNode<E>> list = new ArrayList<>();
    TreeNode<E> current = root;

    while (current != null) {
      list.add(current);
      if (e.compareTo(current.element) < 0) {
        current = current.left;
      } else if (e.compareTo(current.element) > 0) {
        current = current.right;
      } else {
        break;
      }
    }

    return list;
  }

  @Override
  public boolean delete(E e) {
    TreeNode<E> parent = null;
    TreeNode<E> current = root;
    while (current != null) {
      if (e.compareTo(current.element) < 0) {
        parent = current;
        current = current.left;
      } else if (e.compareTo(current.element) > 0) {
        parent = current;
        current = current.right;
      } else {
        break;
      }
    }

    if (current == null) {
      return false;
    }

    if (current.left == null) {
      if (parent == null) {
        root = current.right;
      } else {
        if (e.compareTo(parent.element) < 0) {
          parent.left = current.right;
        } else {
          parent.right = current.right;
        }
      }
    } else {
      TreeNode<E> parentOfRightmost = current;
      TreeNode<E> rightmost = current.left;

      while (rightmost.right != null) {
        parentOfRightmost = rightmost;
        rightmost = rightmost.right;
      }

      current.element = rightmost.element;

      if (parentOfRightmost.right == rightmost) {
        parentOfRightmost.right = rightmost.left;
      } else {
        parentOfRightmost.left = rightmost.left;
      }
    }

    size--;
    return true;
  }

  @Override
  public Iterator<E> iterator() {
    return new InorderIterator();
  }

  private class InorderIterator implements Iterator<E> {
    private ArrayList<E> list = new ArrayList<>();
    private int current = 0;

    public InorderIterator() {
      inorder();
    }

    private void inorder() {
      inorder(root);
    }

    private void inorder(TreeNode<E> root) {
      if (root == null) { return; }
      inorder(root.left);
      list.add(root.element);
      inorder(root.right);
    }

    @Override
    public boolean hasNext() {
      if (current < list.size()) {
        return true;
      }
      return false;
    }

    @Override
    public E next() {
      return list.get(current++);
    }

    @Override
    public void remove() {
      delete(list.get(current));
      list.clear();
      inorder();
    }
  }

  public void clear() {
    root = null;
    size = 0;
  }
}
  public static abstract class AbstractTree<E> implements Tree<E> {
  @Override
  public void inorder() {
  }

  @Override
  public void postorder() {
  }

  @Override
  public void preorder() {
  }

  @Override
  public boolean isEmpty() {
    return getSize() == 0;
  }
}
 public static interface Tree<E> extends Iterable<E> {
  public boolean search(E e);
  public boolean insert(E e);
  public boolean delete(E e);
  public void inorder();
  public void postorder();
  public void preorder();
  public int getSize();
  public boolean isEmpty();
} 
  public static class BTView extends Pane {
  private BST<Integer> tree = new BST<>();
  private double radius = 15;
  private double vGap = 50;

  BTView(BST<Integer> tree) {
    this.tree = tree;
    setStatus("Tree is empty");
  }

  public void setStatus(String msg) {
    getChildren().add(new Text(20, 40, msg));
  }

  public void displayTree() {
    this.getChildren().clear();
    if (tree.getRoot() != null) {
      displayTree(tree.getRoot(), getWidth() / 2, vGap, getWidth() / 4);
    }
  }

  public void displayTree(BST.TreeNode<Integer> root, double x, double y,
    double hGap) {
    if (root.left != null) {
      getChildren().add(new Line(x - hGap, y + vGap, x, y));
      displayTree(root.left, x - hGap, y + vGap, hGap / 2);
    }

    if (root.right != null) {
      getChildren().add(new Line(x + hGap, y + vGap, x, y));
      displayTree(root.right, x + hGap, y + vGap, hGap / 2);
    }

    Circle circle = new Circle(x, y, radius);
    circle.setFill(Color.WHITE);
    circle.setStroke(Color.BLACK);
    getChildren().addAll(circle, new Text(x - 4, y + 4, root.element + ""));
  }

  public  void displayOrder(Order order) {
    StringBuilder sb = new StringBuilder();
    if (order.equals(Order.INORDER)) {
      sb.append("Inorder: [");
      for (Integer num: tree.inorderList()) {
        sb.append(num + " ");
      }
      sb.append("]");
    } else if (order.equals(Order.PREORDER)) {
      sb.append("Preorder: [");
      for (Integer num: tree.preorderList()) {
        sb.append(num + " ");
      }
      sb.append("]");
    } else if (order.equals(Order.POSTORDER)) {
      sb.append("Postorder: [");
      for (Integer num: tree.postorderList()) {
        sb.append(num + " ");
      }
      sb.append("]");
    }
    getChildren().add(new Text(getWidth() / 2 - 50, 20, sb.toString()));
  }

  enum Order {
    INORDER, PREORDER, POSTORDER;
  }
}
  
}