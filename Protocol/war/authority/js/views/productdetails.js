directory.ProductView = Backbone.View.extend({

    render: function () {
        this.$el.html(this.template(this.model.toJSON()));
        return this;
    }
});

directory.ProductSummaryView = Backbone.View.extend({

    initialize:function (options) {
    	_.bindAll(this);
    	this.model = new directory.Product();
        this.model.on("change", this.render, this);
        this.collection = options.collection;
    },

    render:function () {
        this.$el.html(this.template(this.model.toJSON()));
        
        this.$name = this.$el.find('#name');
        this.$desc = this.$el.find('#description');
        this.$price = this.$el.find('#price');
        this.$cert = this.$el.find('#cert');
        this.$content = this.$('#content');
        
        //this.$("#createProduct").click(this.createProduct);

        return this;
    },

    updateSaveModel:function()
    {
    	var data = {
    			name : this.$name.val(),
    			description : this.$desc.val(),
    			price: parseInt(this.$price.val()),
    			cert : this.$cert.val(),
    			content: this.$content.val()
    	};
    	this.model.save(data);
    	this.collection.add(this.model);
    },

    setModel:function(newModel)
    {
    	this.model = newModel;
    	this.render();
    },
    
    createProduct: function()
    {
    	this.updateSaveModel();
    },
    
    events: {
    	"click #saveProduct" : this.createProduct
    }

});